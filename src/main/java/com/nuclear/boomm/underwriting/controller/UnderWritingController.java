package com.nuclear.boomm.underwriting.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인수심사", description = "인수심사 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/uw")
public class UnderWritingController {

    @Operation(summary = "인수심사 대시보드", description = "인수심사 진행/완료/거절 등 현황 집계")
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Object>> getDashboard(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "인수심사 케이스 목록", description = "상태별 인수심사 케이스 목록 조회")
    @GetMapping("/cases")
    public ResponseEntity<ApiResponse<Object>> getCases(
            @RequestParam(required = false) UnderWritingStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "인수심사 케이스 상세", description = "인수심사 케이스 단건 상세 조회")
    @GetMapping("/cases/{id}")
    public ResponseEntity<ApiResponse<Object>> getCaseDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "인수심사 담당자 할당", description = "인수심사 케이스에 담당자(Underwriter) 할당")
    @PostMapping("/cases/{id}/assign")
    public ResponseEntity<ApiResponse<Object>> assignUnderwriter(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        Long userId = 100L; // 권한: 인수심사 관리자/담당자 (임시)
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "인수심사 시작", description = "케이스 상태를 진행중으로 변경")
    @PostMapping("/cases/{id}/start")
    public ResponseEntity<ApiResponse<Object>> startUnderwriting(
            @PathVariable Long id
    ) {
        Long userId = 100L; // 권한: 인수심사 담당자 (임시)
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "인수심사 승인", description = "케이스를 승인 처리하고 완료 상태로 변경")
    @PostMapping("/cases/{id}/approve")
    public ResponseEntity<ApiResponse<Object>> approve(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        Long userId = 100L;
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "인수심사 거절", description = "케이스를 거절 처리하고 거절 사유를 저장")
    @PostMapping("/cases/{id}/reject")
    public ResponseEntity<ApiResponse<Object>> reject(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        Long userId = 100L;
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "거절 사유 목록", description = "거절 사유 코드 목록 조회")
    @GetMapping("/reject-reasons")
    public ResponseEntity<ApiResponse<Object>> getRejectReasons() {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "서류 보완 요청", description = "필요 서류 보완 요청 전송")
    @PostMapping("/cases/{id}/documents/request")
    public ResponseEntity<ApiResponse<Object>> requestDocuments(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        Long userId = 100L;
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "금감원 전송 요청", description = "금감원(FSS)으로 계약 내용을 전송 요청")
    @PostMapping("/cases/{id}/fss/send")
    public ResponseEntity<ApiResponse<Object>> sendToFss(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        Long userId = 100L;
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "금감원 전송 상태 조회", description = "금감원 전송 처리 상태 조회")
    @GetMapping("/cases/{id}/fss/status")
    public ResponseEntity<ApiResponse<Object>> getFssStatus(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
