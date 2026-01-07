package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.dto.request.product.CoverageRequest;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageRequest;
import com.nuclear.boomm.product.dto.response.product.CoverageResponse;
import com.nuclear.boomm.product.dto.response.product.ProductFileResponse;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageFileResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.product.CoverageRepository;
import com.nuclear.boomm.product.repository.feedback.FeedbackRepository;
import com.nuclear.boomm.product.repository.product.ProductFileRepository;
import com.nuclear.boomm.product.repository.product.ProductRepository;
import com.nuclear.boomm.product.repository.product.RiskReportRepository;
import com.nuclear.boomm.product.repository.product.SystemAndRegulationPrepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FeedbackRepository feedbackRepository;
    private final CoverageRepository coverageRepository;
    private final ProductFileRepository productFileRepository;
    private final RiskReportRepository riskReportRepository;
    private final SystemAndRegulationPrepRepository systemAndRegulationPrep;

    private final FileService fileService;

    @Transactional
    public Long createProduct(Long userId) {
        Product product = Product.builder()
                .userId(userId)
                .build();

        return productRepository.save(product).getProductId();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductCoverageResponse save(
            Long userId,
            ProductCoverageRequest request,
            List<MultipartFile> files
    ) {
        Long productId = request.product().productId();

        Product product = productRepository.findByProductIdAndUserId(productId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        product.update(request.product());

        // minIO에 있는 해당 상품 관련 파일들 삭제
        deleteProductFiles(productId);

        // DB에 있는 해당 상품 관련 파일들 삭제
        productFileRepository.deleteByProductId(productId);

        // 새롭게 요청받은 상품 관련 파일들 minIO에 업로드
        uploadProductFiles(userId, productId, files);

        // 상품에 대한 담보 업데이트
        List<Long> coverageIds = request.coverage()
                .stream()
                .map(CoverageRequest::id)
                .toList();

        List<Coverage> coverages = coverageRepository.findByProductIdAndCoverageIdIn(productId, coverageIds);
        coverageRepository.deleteAllByProductIdAndCoverageIdNotIn(productId, coverageIds);

        // 검색을 위해 List -> Map 자료형으로 변경
        Map<Long, Coverage> coverageMap = coverages.stream()
                .collect(Collectors.toMap(Coverage::getCoverageId, Function.identity()));

        // 업데이트 진행
        List<Coverage> responseCoverages = new ArrayList<>();
        for (CoverageRequest coverageRequest : request.coverage()) {
            Coverage coverage = coverageMap.get(coverageRequest.id());

            if (coverage != null) {
                // DB에 값이 있는 경우 -> update 필요
                coverage.update(coverageRequest);
                responseCoverages.add(coverage);
            } else {
                // DB에 값이 없는 경우 -> save 필요
                Coverage newCoverage = Coverage.builder()
                        .category(coverageRequest.category())
                        .productId(coverageRequest.productId())
                        .title(coverageRequest.title())
                        .description(coverageRequest.description())
                        .minCoverageLimit(coverageRequest.minCoverageLimit())
                        .maxCoverageLimit(coverageRequest.maxCoverageLimit())
                        .isMandatory(coverageRequest.isMandatory())
                        .damageCalStandard(coverageRequest.damageCalStandard())
                        .build();
                coverageRepository.save(newCoverage);

                responseCoverages.add(newCoverage);
            }
        }

        if (request.product().isDone()) {
            // product의 isDone true로 변경
            product.updateIsDone(true);
        }

        return ProductCoverageResponse.from(
                product,
                responseCoverages
        );
    }

    public List<ProductResponse> getReleasedProducts() {
        return productRepository
                .findAllByIsReleasedTrue()
                .stream()
                .map(Product::from)
                .toList();
    }

    public List<ProductResponse> getNotReleasedProducts() {

        return productRepository
                .findAllByIsReleasedFalse()
                .stream()
                .map(Product::from)
                .toList();
    }

    public ProductCoverageFileResponse getProductDetails(Long productId) {
        ProductResponse productResponse = ProductResponse.from(productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND)));

        List<ProductFileResponse> productFileList = ProductFileResponse.from(productFileRepository.findAllByProductId(productId));
        List<CoverageResponse> coverageResponseList = CoverageResponse.from(coverageRepository.findAllByProductId(productId));

        return new ProductCoverageFileResponse(
                productResponse,
                coverageResponseList,
                productFileList
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponse deleteUnReleasedProduct(Long productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        if (product.isReleased()) {
            throw new CustomException(ErrorCode.PRODUCT_IS_RELEASED);
        }

        deleteProductFiles(productId);

        productFileRepository.deleteAllByProductId(productId);
        coverageRepository.deleteAllByProductId(productId);
        feedbackRepository.deleteAllByProduct_ProductId(productId);
        riskReportRepository.deleteAllByProductId(productId);
        systemAndRegulationPrep.deleteAllByProductId(productId);

        productRepository.delete(product);

        return ProductResponse.from(product);
    }

    public void uploadProductFiles(Long userId, Long productId, List<MultipartFile> files) {
        try {
            List<ProductFile> productFileList = fileService.uploadFiles(
                    userId,
                    productId,
                    files
            );
            productFileRepository.saveAll(productFileList);
        } catch (IOException e) {
            log.error("파일 업로드 실패: productId: {}", productId, e);

            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    public void deleteProductFiles(Long productId) {
        try {
            fileService.deleteFiles(productFileRepository.findAllByProductId(productId)
                    .stream()
                    .map(ProductFile::getUuidName)
                    .toList());
        } catch (Exception e) {
            log.warn("삭제 대상 파일 없음 (무시하고 진행): {}", e.getMessage());
        }
    }
}
