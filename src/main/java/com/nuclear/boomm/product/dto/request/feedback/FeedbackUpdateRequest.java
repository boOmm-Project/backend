package com.nuclear.boomm.product.dto.request.feedback;

import jakarta.validation.constraints.NotNull;

public record FeedbackUpdateRequest(
        @NotNull
        String description
) {
}
