package com.nuclear.boomm.product.service;

import com.nuclear.boomm.common.error.ErrorCode;
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
import com.nuclear.boomm.product.repository.feedback.FeedbackRepository;
import com.nuclear.boomm.product.repository.product.CoverageRepository;
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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final FeedbackRepository feedbackRepository;
    private final CoverageRepository coverageRepository;
    private final ProductFileRepository productFileRepository;
    private final RiskReportRepository riskReportRepository;
    private final SystemAndRegulationPrepRepository systemAndRegulationPrep;

    private final FileService fileService;

    @Transactional(rollbackFor = Exception.class)
    public ProductResponse createProduct(Long userId) {
        // 상품 생성
        Product product = Product.builder().userId(userId).build();

        // 상품 저장
        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductCoverageResponse save(Long userId, ProductCoverageRequest request, List<MultipartFile> files, Long productId) {
        // 사용자 검증
        Product product = productRepository.findByProductIdAndUserId(productId, userId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // 상품 업데이트
        product.update(request.product());

        // minIO에 있는 해당 상품 관련 파일들 삭제
        deleteProductFiles(productId);

        // DB에 있는 해당 상품 관련 파일들 삭제
        productFileRepository.deleteByProductId(productId);

        // 새롭게 요청받은 상품 관련 파일들 minIO에 업로드
        uploadProductFiles(userId, productId, files);

        // 담보 업데이트
        List<Coverage> updatedCoverages = updateCoverages(request.coverage(), productId);

        if (request.product().isDone()) {
            // product의 isDone true로 변경
            product.updateIsDone(true);
        }

        return ProductCoverageResponse.from(product, updatedCoverages);
    }

    public List<ProductResponse> getReleasedProducts() {
        return productRepository.findAllByIsReleasedTrue().stream().map(Product::from).toList();
    }

    public List<ProductResponse> getNotReleasedProducts() {
        return productRepository.findAllByIsReleasedFalse().stream().map(Product::from).toList();
    }

    public ProductCoverageFileResponse getProductDetails(Long productId) {
        // 사용자 검증
        ProductResponse productResponse = ProductResponse.from(productRepository
                .findByProductIdAndIsReleasedTrue(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND)
                )
        );

        List<ProductFileResponse> productFileList = ProductFileResponse.from(productFileRepository.findAllByProductId(productId));
        List<CoverageResponse> coverageResponseList = CoverageResponse.from(coverageRepository.findAllByProductId(productId));

        return new ProductCoverageFileResponse(productResponse, coverageResponseList, productFileList);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponse deleteUnReleasedProduct(Long productId) {
        // 사용자 검증
        Product product = productRepository.findByProductIdAndIsReleasedFalse(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // 상품 관련 파일 삭제
        deleteProductFiles(productId);

        // DB에서 상품 관련 데이터 삭제
        deleteAllFromRepositories(productId);

        // 상품 자체 삭제
        productRepository.delete(product);

        return ProductResponse.from(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void uploadProductFiles(Long userId, Long productId, List<MultipartFile> files) {
        try {
            List<ProductFile> productFileList = fileService.uploadFiles(userId, productId, files);
            productFileRepository.saveAll(productFileList);
        } catch (IOException e) {
            log.error("파일 업로드 실패: productId: {}", productId, e);

            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    public ProductCoverageFileResponse getUnReleasedProductDetails(Long productId) {
        // 사용자 검증 & 상품 조회
        ProductResponse productResponse = ProductResponse.from(productRepository.findByProductIdAndIsReleasedFalse(productId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND)));

        List<ProductFileResponse> productFileList = ProductFileResponse.from(productFileRepository.findAllByProductId(productId));
        List<CoverageResponse> coverageResponseList = CoverageResponse.from(coverageRepository.findAllByProductId(productId));

        return new ProductCoverageFileResponse(productResponse, coverageResponseList, productFileList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductFiles(Long productId) {
        try {
            fileService.deleteFiles(productFileRepository.findAllByProductId(productId).stream().map(ProductFile::getUuidName).toList());
        } catch (Exception e) {
            log.warn("삭제 대상 파일 없음 (무시하고 진행): {}", e.getMessage());
        }
    }

    private void deleteAllFromRepositories(Long productId) {
        productFileRepository.deleteAllByProductId(productId);
        coverageRepository.deleteAllByProductId(productId);
        feedbackRepository.deleteAllByProduct_ProductId(productId);
        riskReportRepository.deleteAllByProduct_ProductId(productId);
        systemAndRegulationPrep.deleteAllByProductId(productId);
    }

    private List<Coverage> updateCoverages(List<CoverageRequest> coverages, Long productId) {
        // productId에 맞는 Coverage 조회
        List<Coverage> coverageList = coverageRepository.findAllByProductId(productId);

        // Coverage 리스트 Map화
        Map<Long, Coverage> coverageMap = coverageList.stream()
                .collect(Collectors.toMap(Coverage::getCoverageId, Function.identity()));

        // 요청 리스트 중 coverageId!=0(이미 DB에 존재하는 담보)인 값들을 Map으로 변환
        Map<Long, CoverageRequest> coverageRequestMap = coverages.stream()
                .filter(coverageRequest -> coverageRequest.coverageId() != 0)
                .collect(Collectors.toMap(CoverageRequest::coverageId, Function.identity()));

        // 요청 리스트 중 coverageId==0(신규 담보)인 값들을 따로 분리
        List<CoverageRequest> coverageRequests = coverages.stream()
                .filter(coverageRequest -> coverageRequest.coverageId() == 0)
                .collect(Collectors.toList());

        // coverageId = 0인 신규 추가인 담보 저장
        coverageRepository.saveAll(Coverage.create(coverageRequests));

        // 값 업데이트
        for (Coverage coverage : coverageList) {
            // coverageId로 값 찾아서 업데이트
            coverage.update(coverageRequestMap.get(coverage.getCoverageId()));

            // DB Map에서 업데이트한 key-value 제거
            coverageMap.remove(coverage.getCoverageId());
        }

        // 요청에 없는 담보 삭제
        coverageRepository.deleteAllByCoverageIdIn(coverageMap.keySet());

        return coverageList;
    }
}
