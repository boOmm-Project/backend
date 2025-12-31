package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.repository.FeedbackRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

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
}
