package com.nuclear.boomm.product.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.product.dto.response.product.RiskReportResponse;
import com.nuclear.boomm.product.service.RiskReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "위험 보고서", description = "위험 보고서 생성, 수정, 피드백 반영, 삭제 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@RestController
public class RiskReportController {
    private final RiskReportService riskReportService;

    @Operation(summary = "위험 보고서 생성", description = "상품 관리자가 위험 보고서를 생성")
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<RiskReportResponse>> createRiskReport(
            @PathVariable("id") Long productId
    ) {
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(riskReportService.createRiskReport(userId, productId)));
    }

    @Operation(summary = "위험 보고서 조회", description = "위험 보고서의 세부 내용 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RiskReportResponse>> getRiskReport(
            @PathVariable("id") Long productId
    ) {
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(riskReportService.getRiskReport(userId, productId)));
    }
}
