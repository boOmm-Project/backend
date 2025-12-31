package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.ExtraDescription;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackExtraDescriptionRequest;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackUpdateRequest;
import com.nuclear.boomm.product.dto.response.feedback.ExtraDescriptionResponse;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.ExtraDescriptionRepository;
import com.nuclear.boomm.product.repository.FeedbackRepository;
import com.nuclear.boomm.product.repository.ProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final ProductRepository productRepository;
    private final FeedbackRepository feedbackRepository;
    private final ExtraDescriptionRepository extraDescriptionRepository;

    @Transactional(rollbackFor = Exception.class)
    public FeedbackResponse createFeedback(Long userId, @NotNull Long productId) {
        // productId로 존재하는 product 있는지 검증해야함
        if (!productRepository.existsByProductId(productId)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return FeedbackResponse.from(
                feedbackRepository.save(
                        Feedback.builder()
                                .productId(productId)
                                .writerId(userId)
                                .build()
                )
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public FeedbackResponse updateFeedback(Long userId, Long feedbackId, FeedbackUpdateRequest request) {
        Feedback feedback = feedbackRepository.findByFeedbackIdAndWriterId(feedbackId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE));

        feedback.updateDescription(request.description());
        feedback.updateStatus(FeedbackStatus.STAKEHOLDER_FEEDBACK_UPDATE_PENDING);

        return FeedbackResponse.from(feedback);
    }

    public List<FeedbackResponse> getAllStakeholderFeedbacks(Long userId) {
        return FeedbackResponse.from(feedbackRepository.findAllByWriterId(userId));
    }

    public FeedbackResponse getProductManagerFeedback(Long userId, Long productId) {
        if (!productRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new CustomException(ErrorCode.FEEDBACK_NOT_FOUND);
        }

        return FeedbackResponse.from(
                feedbackRepository.findByProductId(productId)
                        .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND))
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public ExtraDescriptionResponse requestExtraDescription(Long userId, Long feedbackId, Long productId, FeedbackExtraDescriptionRequest request) {
        if (!feedbackRepository.existsByFeedbackId(feedbackId)) {
            throw new CustomException(ErrorCode.FEEDBACK_NOT_FOUND);
        } else if (!productRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return ExtraDescriptionResponse.from(
                extraDescriptionRepository.save(
                        ExtraDescription.builder()
                                .feedbackId(feedbackId)
                                .productId(productId)
                                .request(request.request())
                                .constructor(userId)
                                .build()
                )
        );
    }
}
