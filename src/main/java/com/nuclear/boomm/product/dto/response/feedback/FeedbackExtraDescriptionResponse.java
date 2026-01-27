package com.nuclear.boomm.product.dto.response.feedback;

import com.nuclear.boomm.product.domain.ExtraDescription;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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
        Long creatorId
) {
    public static FeedbackExtraDescriptionResponse from(ExtraDescription extraDescription) {
        return new FeedbackExtraDescriptionResponse(
                extraDescription.getFeedbackId(),
                extraDescription.getProductId(),
                extraDescription.getRequest(),
                extraDescription.getResponse(),
                extraDescription.getIsResolved(),
                extraDescription.getCreatorId()
        );
    }

    public static List<FeedbackExtraDescriptionResponse> from(List<ExtraDescription> extraDescriptions) {
        return extraDescriptions.stream()
                .map(FeedbackExtraDescriptionResponse::from)
                .toList();
    }
}
