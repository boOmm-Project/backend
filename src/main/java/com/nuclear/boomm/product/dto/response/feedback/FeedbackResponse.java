package com.nuclear.boomm.product.dto.response.feedback;

import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackResponse(
        @NotNull
        Long feedbackId,

        @NotNull
        FeedbackStatus status,

        @NotBlank
        String description,

        @NotNull
        Long productId,

        @NotNull
        Long writerId
) {
    public static FeedbackResponse from(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getFeedbackId(),
                feedback.getStatus(),
                feedback.getDescription(),
                feedback.getProductId(),
                feedback.getWriterId()
        );
    }
}
