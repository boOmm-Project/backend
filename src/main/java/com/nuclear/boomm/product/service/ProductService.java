package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.dto.request.ProductRequest;
import com.nuclear.boomm.product.dto.response.ProductResponse;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.FeedbackRepository;
import com.nuclear.boomm.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FeedbackRepository feedbackRepository;

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
    public ProductResponse save(UserDetails userDetails, ProductRequest request) {
        String username = userDetails.getUsername();
        Long userId = Long.parseLong(username);

        // 사용자가 기획중인 상품 있는지 확인
        Product product = productRepository.findByUserIdAndProductId(userId, request.productId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        product.updateProduct(request);
        if (request.isDone()) {
            // 임시 저장이 아닌 최종 저장일 때만 isDone 상태 변경
            product.updateIsDone(true);

            // 피드백 생성
            Feedback feedback = Feedback.builder()
                    .productId(request.productId())
                    .writerId(request.stakeholderId())
                    .build();
            feedbackRepository.save(feedback);
        }

        return ProductResponse.from(product);
    }
}
