package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.ExtraDescription;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackExtraDescriptionRequest;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackUpdateRequest;
import com.nuclear.boomm.product.dto.request.product.ProductRequest;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackExtraDescriptionDetailResponse;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackExtraDescriptionResponse;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.product.repository.feedback.FeedbackRepository;
import com.nuclear.boomm.product.repository.product.ExtraDescriptionRepository;
import com.nuclear.boomm.product.repository.product.ProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {

    private final ProductRepository productRepository;
    private final FeedbackRepository feedbackRepository;
    private final ExtraDescriptionRepository extraDescriptionRepository;

    @Transactional(rollbackFor = Exception.class)
    public FeedbackResponse createFeedback(Long userId, @NotNull Long productId) {
        // 사용자 검증
        Product product = productRepository.findByProductIdAndUserId(productId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // 이해관계자의 피드백 생성 및 저장
        Feedback savedFeedback = feedbackRepository.save(Feedback.create(userId, product, "STAKEHOLDER"));

        // dto 반환
        return FeedbackResponse.from(savedFeedback);
    }

    @Transactional(rollbackFor = Exception.class)
    public FeedbackResponse updateFeedback(
            Long userId,
            Long feedbackId,
            FeedbackUpdateRequest request
    ) {
        // 사용자 검증
        Feedback feedback = feedbackRepository.findByFeedbackIdAndWriterId(feedbackId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE));

        // 피드백 업데이트
        feedback.updateDescription(request.description());
        feedback.updateStatus(FeedbackStatus.STAKEHOLDER_FEEDBACK_UPDATE_PENDING);

        return FeedbackResponse.from(feedback);
    }

    @Transactional(rollbackFor = Exception.class)
    public FeedbackExtraDescriptionResponse requestExtraDescription(
            Long userId,
            Long feedbackId,
            Long productId,
            FeedbackExtraDescriptionRequest request
    ) {
        // 사용자 검증
        Feedback feedback = feedbackRepository.findByFeedbackIdAndProduct_ProductId(feedbackId, productId)
                .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND));

        // Feedback 상태 변경
        feedback.updateStatus(FeedbackStatus.ADDITIONAL_EXPLANATION_REQUEST);

        // ExtraDescription 생성
        ExtraDescription savedExtraDescription = extraDescriptionRepository.save(
                ExtraDescription.create(
                        request,
                        feedbackId,
                        productId,
                        userId
                )
        );

        // ExtraDescription 생성, 저장, 반환
        return FeedbackExtraDescriptionResponse.from(savedExtraDescription);
    }

    @Transactional(rollbackFor = Exception.class)
    public FeedbackExtraDescriptionResponse responseExtraDescription(
            Long userId,
            Long feedbackId,
            Long extraDescriptionId,
            FeedbackExtraDescriptionRequest request
    ) {
        // 사용자 검증
        ExtraDescription extraDescription = extraDescriptionRepository.findByExtraDescriptionIdAndFeedbackId(extraDescriptionId, feedbackId)
                .orElseThrow(() -> new CustomException(ErrorCode.EXTRA_DESCRIPTION_NOT_FOUND));

        Feedback feedback = feedbackRepository.findByFeedbackIdAndWriterId(feedbackId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND));

        // 추가 설명 업데이트
        extraDescription.updateResponse(request.description());

        // 피드백 상태 업데이트
        feedback.updateStatus(FeedbackStatus.ADDITIONAL_EXPLANATION_UPDATE_PENDING);

        return FeedbackExtraDescriptionResponse.from(extraDescription);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponse reflectFeedback(Long userId, Long feedbackId, Long productId, ProductRequest request) {
        Product product = productRepository.findByProductIdAndUserId(productId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        Feedback feedback = feedbackRepository.findByFeedbackIdAndProduct_ProductId(feedbackId, productId)
                .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND));

        product.update(request);
        product.updateIsDone(request.isDone());

        feedback.updateStatus(FeedbackStatus.STAKEHOLDER_FEEDBACK_UPDATE);

        return ProductResponse.from(product);
    }

    public List<FeedbackResponse> getAllStakeholderFeedbacks(Long userId) {
        return FeedbackResponse.from(feedbackRepository.findAllByWriterId(userId));
    }

    public List<FeedbackResponse> getProductManagerFeedback(Long userId) {
        if (!productRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return FeedbackResponse.from(
                feedbackRepository.searchAllFeedbackByUserIdWithProduct(userId)
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponse approveProduct(Long userId, Long productId) {
        // 해당 상품의 이해관계자가 userId의 사용자가 맞는지 검증
        List<Feedback> feedbacks = feedbackRepository.findAllByProduct_ProductIdAndWriterId(productId, userId);
        if (feedbacks.isEmpty()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // 해당 상품의 모든 피드백 상태 변경
        feedbacks.forEach(feedback -> {
            feedback.getProduct().approve();
            feedback.updateStatus(FeedbackStatus.APPROVAL);
        });

        // 상품 승인
        Product approvedProduct = feedbacks.get(0).getProduct();
        approvedProduct.release();

        return ProductResponse.from(approvedProduct);
    }

    public List<FeedbackExtraDescriptionResponse> getAllExtraDescriptions(Long userId, Long productId) {
        // TODO: Role로 검증하는 로직 추가

        // 추가 설명 리스트 조회
        List<ExtraDescription> descriptions = extraDescriptionRepository.findAllByProductId(productId);

        return FeedbackExtraDescriptionResponse.from(descriptions);
    }

    public FeedbackExtraDescriptionDetailResponse getExtraDescriptionDetails(Long userId, Long extraDescriptionId) {
        // TODO: Role로 검증하는 로직 추가

        // 추가 설명 조회
        ExtraDescription description = extraDescriptionRepository.findByExtraDescriptionId(extraDescriptionId)
                .orElseThrow(() -> new CustomException(ErrorCode.EXTRA_DESCRIPTION_NOT_FOUND));

        return FeedbackExtraDescriptionDetailResponse.from(description);
    }
}
