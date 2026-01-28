package com.nuclear.boomm.underwriting.controller;

import com.nuclear.boomm.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "손해율", description = "손해율 분석 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loss-ratio")
public class LossRatioController {

    // private final LossRatioService lossRatioService;

    @Operation(summary = "손해율 분석 초기 화면", description = "손해율 분석 입력 폼에 필요한 기본 항목 제공")
    @GetMapping("/form")
    public ResponseEntity<ApiResponse<Object>> getForm() {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "손해율 분석 컨텍스트 조회", description = "손해율 분석에 필요한 고객/보험 정보 기본 데이터 묶음 조회")
    @GetMapping("/context")
    public ResponseEntity<ApiResponse<Object>> getAnalysisContext(
            @RequestParam(required = false) Long underwritingReviewId,
            @RequestParam(required = false) Long contractId
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "손해율 분석 실행", description = "입력된 보험 정보로 손해율을 계산하고 결과를 반환")
    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<Object>> analyzeLossRatio(
            @RequestBody(required = false) Object body
    ) {
        Long userId = 100L;
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
