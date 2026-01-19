package com.nuclear.boomm.product.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.product.dto.request.feedback.RiskReportFeedbackRequest;
import com.nuclear.boomm.product.dto.request.product.RiskReportUpdateRequest;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.dto.response.product.RiskReportDetailResponse;
import com.nuclear.boomm.product.dto.response.product.RiskReportResponse;
import com.nuclear.boomm.product.service.RiskReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "위험 보고서", description = "위험 보고서 생성, 수정, 피드백 반영, 삭제 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@RestController
public class RiskReportController {
    private final RiskReportService riskReportService;

    @Operation(summary = "위험 보고서 생성", description = "상품 관리자가 위험 보고서를 생성")
    @PostMapping("/create/{product-id}/{compliance-id}")
    public ResponseEntity<ApiResponse<RiskReportResponse>> createRiskReport(
            @PathVariable("product-id") Long productId,
            @PathVariable("compliance-id") Long complianceId
    ) {
        // 권한: 상품 관리자
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(riskReportService.createRiskReport(userId, productId, complianceId)));
    }

    @Operation(summary = "위험 보고서 조회", description = "위험 보고서의 세부 내용 조회")
    @GetMapping("/{report-id}")
    public ResponseEntity<ApiResponse<RiskReportDetailResponse>> getRiskReport(
            @PathVariable("report-id") Long reportId
    ) {
        // 권한: 상품 관리자, 컴플라이언스
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(riskReportService.getRiskReportDetails(userId, reportId)));
    }

    @Operation(summary = "위험 보고서 피드백 생성", description = "해당 상품의 위험 보고서에 대한 피드백 생성")
    @PostMapping("/{report-id}/{product-id}")
    public ResponseEntity<ApiResponse<FeedbackResponse>> createRiskReportFeedback(
            @PathVariable("report-id") Long reportId,
            @PathVariable("product-id") Long productId
    ) {
        // 권한: 컴플라이언스
        Long complianceId = 100L;

        return ResponseEntity.ok(ApiResponse.success(riskReportService.createRiskReportFeedback(complianceId, productId, reportId)));
    }

    @Operation(summary = "위험 보고서에 대한 피드백 전송", description = "컴플라이언스가 상품에 대한 위험 보고서 피드백 전송")
    @PatchMapping("/{report-id}/{feedback-id}")
    public ResponseEntity<ApiResponse<Long>> feedbackRiskReport(
            @PathVariable("report-id") Long reportId,
            @PathVariable("feedback-id") Long feedbackId,
            @RequestBody @Valid RiskReportFeedbackRequest request
    ) {
        // 권한: 컴플라이언스
        Long complianceId = 100L;

        return ResponseEntity.ok(ApiResponse.success(riskReportService.feedbackRiskReport(reportId, complianceId, feedbackId, request)));
    }

    @Operation(summary = "위험 보고서 피드백 업데이트", description = "피드백 내용을 위험 보고서에 업데이트")
    @PatchMapping("/{report-id}")
    public ResponseEntity<ApiResponse<RiskReportResponse>> updateRiskReport(
            @PathVariable("report-id") Long reportId,
            @RequestBody @Valid RiskReportUpdateRequest request
    ) {
        // 권한: 상품 관리자
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(riskReportService.updateRiskReport(userId, reportId, request)));
    }
}
