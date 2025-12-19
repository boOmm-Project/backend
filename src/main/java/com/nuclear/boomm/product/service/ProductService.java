package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.dto.request.ProductRequest;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageFileRequest;
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
import org.springframework.security.core.userdetails.UserDetails;
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
    public Long createProduct(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Long userId = Long.parseLong(username);

        Product product = Product.builder()
                .userId(userId)
                .build();

        return productRepository.save(product).getProductId();
    }

    @Transactional
    public ProductResponse save(UserDetails userDetails, ProductCoverageFileRequest request) {
        String username = userDetails.getUsername();
        Long userId = Long.parseLong(username);

        Product product = productRepository.findByProductIdAndUserId(request.product().productId(), userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        product.updateProduct(request.product());

        List<ProductFile> fileList = productFileRepository
                .findAllByProductId(request.product().productId())
                .stream()
                .map(productFile -> {
                    productFileRepository.delete(productFile);
                    return ProductFile.update(productFile);
                })
                .toList();
        productFileRepository.saveAll(fileList);

        List<Coverage> coverageList = coverageRepository
                .findAllByProductId(request.product().productId())
                .stream()
                .map(coverage -> {
                    coverageRepository.delete(coverage);
                    return Coverage.update(coverage);
                })
                .toList();
        coverageRepository.saveAll(coverageList);

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

    public List<ProductResponse> getReleaseProductList(ProductRequest request) {
        return productRepository
                .findAllByProductIdAndIsDoneTrue(request.productId())
                .stream()
                .map(Product::from)
                .toList();
    }

    public ProductCoverageFileResponse getProductDetails(Long productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        List<ProductFileResponse> productFileList = productFileRepository
                .findAllByProductId(productId)
                .stream()
                .map(ProductFileResponse::from)
                .toList();
        List<Coverage> coverageList = coverageRepository.findAllByProductId(productId);

        return new ProductCoverageFileResponse(
                product,
                coverageList,
                productFileList
        );
    }
}
