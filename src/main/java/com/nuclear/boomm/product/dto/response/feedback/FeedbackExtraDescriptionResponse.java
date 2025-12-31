package com.nuclear.boomm.product.dto.response.feedback;

import com.nuclear.boomm.product.domain.ExtraDescription;
import jakarta.validation.constraints.NotNull;

public record FeedbackExtraDescriptionResponse(
        @NotNull
        Long feedbackId,

        @NotNull
        Long productId,

        @NotNull
        String request,

        @NotNull
        String response,

        @NotNull
        Boolean isResolved,

        @NotNull
        Long constructor
) {
    public static FeedbackExtraDescriptionResponse from(ExtraDescription extraDescription) {
        return new FeedbackExtraDescriptionResponse(
                extraDescription.getFeedbackId(),
                extraDescription.getProductId(),
                extraDescription.getRequest(),
                extraDescription.getResponse(),
                extraDescription.getIsResolved(),
                extraDescription.getConstructor()
        );
    }
}
