package com.nuclear.boomm.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotNull
        Long productId,

        @NotBlank(message = "상품 이름은 필수입니다.")
        String productName,

        @NotNull(message = "카테고리 선택은 필수입니다.")
        Long category,

        @NotBlank(message = "겨냥 고객은 필수입니다.")
        String targetCustomer,

        @NotNull(message = "계약 기간은 필수입니다.")
        int period,

        @NotBlank(message = "판매 채널은 필수입니다.")
        String salesChannel,

        @NotNull
        Long userId,

        @NotNull
        boolean isDone,

        Long stakeholderId,

        @NotNull
        boolean isReleased
) {
}
