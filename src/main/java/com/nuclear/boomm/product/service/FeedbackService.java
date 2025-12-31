package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackUpdateRequest;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
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

    @Transactional(rollbackFor = Exception.class)
    public FeedbackResponse createFeedback(Long userId, @NotNull Long productId) {
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
}
