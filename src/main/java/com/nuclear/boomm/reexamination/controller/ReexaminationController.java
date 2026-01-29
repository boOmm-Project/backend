package com.nuclear.boomm.reexamination.controller;

import com.nuclear.boomm.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "재심사", description = "재심사 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reex")
public class ReexaminationController {

    // 재심사 대시보드
    @Operation(summary = "재심사 대시보드", description = "재심사 현황 요약")
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Object>> getReexDashboard(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "재심사 케이스 목록", description = "재심사 진행중 케이스 목록 조회")
    @GetMapping("/cases")
    public ResponseEntity<ApiResponse<Object>> getReexaminationCases(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/cases/{id}/status")
    public ResponseEntity<ApiResponse<Object>> changeReexStatus(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "재심사 거절 사유 목록", description = "재심사 거절 사유 코드 목록 조회")
    @GetMapping("/reject-reasons")
    public ResponseEntity<ApiResponse<Object>> getRejectReasons() {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}