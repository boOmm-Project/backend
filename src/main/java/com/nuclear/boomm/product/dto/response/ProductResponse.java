package com.nuclear.boomm.product.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductResponse(

        @NotNull
        Long productId,

        @NotBlank
        String productName,

        @NotNull
        Long category,

        @NotBlank
        String targetCustomer,

        @NotNull
        int period,

        @NotBlank
        String salesChannel,

        @NotNull
        Long userId,

        @NotNull
        boolean isDone
) {}
