package com.nuclear.boomm.product.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;
import com.nuclear.boomm.product.service.ComplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "컴플라이언스", description = "상품 출시 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/compliance")
public class ComplianceController {

    private final ComplianceService complianceService;

    // 상품 출시
    @Operation(summary = "상품 출시", description = "이해관계자의 승인이 떨어진 상품을 출시")
    @PatchMapping("/{product-id}")
    public ResponseEntity<ApiResponse<Long>> permitRelease(
            @PathVariable("product-id") Long productId
    ) {
        Long userId = 100L;

        return ResponseEntity.ok(ApiResponse.success(complianceService.permitRelease(userId, productId)));
    }
}
