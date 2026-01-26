package com.nuclear.boomm.product.dto.response.feedback;

import com.nuclear.boomm.product.domain.ExtraDescription;
import jakarta.validation.constraints.NotNull;

public record FeedbackExtraDescriptionDetailResponse(
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
        Long creatorId
) {
    public static FeedbackExtraDescriptionDetailResponse from(ExtraDescription description) {
        return new FeedbackExtraDescriptionDetailResponse(
                description.getFeedbackId(),
                description.getProductId(),
                description.getRequest(),
                description.getResponse(),
                description.getIsResolved(),
                description.getCreatorId()
        );
    }
}
