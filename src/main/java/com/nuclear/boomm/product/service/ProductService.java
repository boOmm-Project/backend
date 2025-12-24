package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageRequest;
import com.nuclear.boomm.product.dto.response.CoverageResponse;
import com.nuclear.boomm.product.dto.response.ProductFileResponse;
import com.nuclear.boomm.product.dto.response.ProductResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageFileResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.CoverageRepository;
import com.nuclear.boomm.product.repository.FeedbackRepository;
import com.nuclear.boomm.product.repository.ProductFileRepository;
import com.nuclear.boomm.product.repository.ProductRepository;
import com.nuclear.boomm.product.repository.RiskReportRepository;
import com.nuclear.boomm.product.repository.SystemAndRegulationPrepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

        // 상품에 대한 담보 전체 삭제
        coverageRepository.deleteAllByProductId(productId);

        // 새롭게 요청받은 상품에 대한 담보 전체 추가
        List<Coverage> coverages = request.coverage().stream()
                .map(coverage -> {
                    return coverageRepository.save(CoverageResponse.from(coverage));
                })
                .toList();

        if (request.product().isDone()) {
            // product의 isDone true로 변경
            product.updateIsDone(true);

            // 피드백 생성
            Feedback feedback = Feedback.builder()
                    .productId(productId)
                    .writerId(request.product().stakeholderId())
                    .build();

            feedbackRepository.save(feedback);
        }

        return ProductCoverageResponse.from(
                product,
                coverages
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

        productFileRepository.deleteAllByProductId(productId);
        coverageRepository.deleteAllByProductId(productId);
        feedbackRepository.deleteAllByProductId(productId);
        riskReportRepository.deleteAllByProductId(productId);
        systemAndRegulationPrep.deleteAllByProductId(productId);

        if (product.isReleased()) {
            throw new CustomException(ErrorCode.PRODUCT_IS_RELEASED);
        }

        deleteProductFiles(productId);

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
                    .map(ProductFile::getUrl)
                    .toList());
        } catch (CustomException e) {
            log.warn("삭제 대상 파일 없음 (무시하고 진행): {}", e.getMessage());
        }
    }
}
