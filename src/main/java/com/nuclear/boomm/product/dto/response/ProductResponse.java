package com.nuclear.boomm.product.dto.response;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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
        boolean isDone,

        Long writerId
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getCategory(),
                product.getTargetCustomer(),
                product.getPeriod(),
                product.getSalesChannel(),
                product.getUserId(),
                product.isDone(),
                null
        );
    }
}
