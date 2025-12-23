package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageRequest;
import com.nuclear.boomm.product.dto.response.CoverageResponse;
import com.nuclear.boomm.product.dto.response.ProductFileResponse;
import com.nuclear.boomm.product.dto.response.ProductResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageFileResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.CoverageRepository;
import com.nuclear.boomm.product.repository.FeedbackRepository;
import com.nuclear.boomm.product.repository.ProductFileRepository;
import com.nuclear.boomm.product.repository.ProductRepository;
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

    private final FileService fileService;

    @Transactional
    public Long createProduct(Long userId) {
        Product product = Product.builder()
                .userId(userId)
                .build();

        return productRepository.save(product).getProductId();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponse save(
            Long userId,
            ProductCoverageRequest request,
            List<MultipartFile> files
    ) {
        Product product = productRepository.findByProductIdAndUserId(request.product().productId(), userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        product.update(request.product());

        // 클라이언트가 임시저장/저장 요청을 보낼 시 일단 저장하고 있던 파일 전부 삭제
        productFileRepository.deleteByProductId(request.product().productId());

        // 클라이언트에게 요청받은 파일들 전부 저장 및 minIO에 업로드
        try {
            List<ProductFile> productFileList = fileService.uploadFiles(
                    request.product().userId(),
                    request.product().productId(),
                    files
            );
            productFileRepository.saveAll(productFileList);
        } catch (IOException e) {
            log.error("파일 업로드 실패: productId: {}", request.product().productId(), e);

            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
        }

        coverageRepository.findAllByProductId(request.product().productId())
                .forEach(coverage -> request.coverage().forEach(coverage::update));

        if (request.product().isDone()) {
            // product의 isDone true로 변경
            product.updateIsDone(true);

            // 피드백 생성
            Feedback feedback = Feedback.builder()
                    .productId(product.getProductId())
                    .writerId(request.product().stakeholderId())
                    .build();

            feedbackRepository.save(feedback);
        }

        return ProductResponse.from(product);
    }

    public List<ProductResponse> getReleasedProducts() {
        return productRepository
                .findAllByIsDoneTrue()
                .stream()
                .map(Product::from)
                .toList();
    }

    public List<ProductResponse> getNotReleasedProducts() {

        return productRepository
                .findAllByIsDoneFalse()
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
}
