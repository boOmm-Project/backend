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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FeedbackRepository feedbackRepository;
    private final CoverageRepository coverageRepository;
    private final ProductFileRepository productFileRepository;

    @Transactional
    public Long createProduct(Long userId) {
        Product product = Product.builder()
                .userId(userId)
                .build();

        return productRepository.save(product).getProductId();
    }

    @Transactional
    public ProductResponse save(Long userId, ProductCoverageFileRequest request) {
        Product product = productRepository.findByProductIdAndUserId(request.product().productId(), userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        product.update(request.product());

        // update에 내용을 수정해 minio에 올리고 변경사항을 db에 저장하는 로직 필요
        productFileRepository.findAllByProductId(request.product().productId())
                .forEach(file -> request.file().forEach(file::update));

        coverageRepository.findAllByProductId(request.product().productId())
                .forEach(coverage -> request.coverage().forEach(coverage::update));

        if (request.product().isDone()) {
            // product의 isDone true로 변경
            product.updateIsDone(true);

            // 피드백 생성
            Feedback feedback = Feedback.builder()
                    .productId(product.getProductId())
                    .writerId(request.stakeholderId())
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
