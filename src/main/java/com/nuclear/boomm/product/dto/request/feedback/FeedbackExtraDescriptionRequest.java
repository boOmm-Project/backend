package com.nuclear.boomm.product.dto.request.feedback;

import jakarta.validation.constraints.NotBlank;

public record FeedbackExtraDescriptionRequest(
        @NotBlank
        String request
) {
}
