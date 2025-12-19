package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.dto.request.ProductRequest;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageFileRequest;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        productFileRepository
                .findAllByProductId(request.product().productId())
                .stream()
                .map(ProductFile::update);

        coverageRepository
                .findAllByProductId(request.product().productId())
                .stream()
                .map(Coverage::updateAll);

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

        return new ProductResponse();
    }

    public List<ProductResponse> getReleaseProductList(ProductRequest request) {
        return productRepository
                .findAllByProductIdAndIsDoneTrue(request.productId())
                .stream()
                .map(Product::from)
                .toList();
    }
}
