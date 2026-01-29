package com.nuclear.boomm.underwriting.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.underwriting.dto.request.ChangeUnderWritingStatusRequest;
import com.nuclear.boomm.underwriting.dto.request.DocumentRequest;
import com.nuclear.boomm.underwriting.dto.response.AssignUnderWriterRequest;
import com.nuclear.boomm.underwriting.dto.response.FssSendResponse;
import com.nuclear.boomm.underwriting.dto.response.FssStatusResponse;
import com.nuclear.boomm.underwriting.dto.response.UnderWritingCaseResponse;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import com.nuclear.boomm.underwriting.service.UnderWritingService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인수심사", description = "인수심사 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/uw/normal")
public class UnderWritingController {

    private final UnderWritingService underWritingService;

    @Operation(summary = "인수심사 대시보드", description = "인수심사 진행/완료/거절 등 현황 집계")
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Object>> getDashboard(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.getDashboard()));
    }

    @Operation(summary = "인수심사 케이스 목록", description = "상태별 인수심사 케이스 목록 조회")
    @GetMapping("/cases")
    public ResponseEntity<ApiResponse<Object>> getCases(
            @RequestParam(required = false) UnderWritingStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.getCases(status, page, size)));
    }

    @Operation(summary = "인수심사 케이스 상세", description = "인수심사 케이스 단건 상세 조회")
    @GetMapping("/cases/{id}")
    public ResponseEntity<ApiResponse<UnderWritingCaseResponse>> getCaseDetail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.getCaseDetail(id)));
    }

    @Operation(summary = "인수심사 담당자 할당", description = "인수심사 케이스에 담당자(Underwriter) 할당")
    @PostMapping("/cases/{id}/assign")
    public ResponseEntity<ApiResponse<Object>> assignUnderwriter(
            @PathVariable Long id,
            @Valid @RequestBody AssignUnderWriterRequest body
    ) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.assignUnderwriter(id, body)));
    }

    @Operation(summary = "인수심사 상태 변경", description = "COMPLETED/REJECTED 처리")
    @PatchMapping("/cases/{id}/status")
    public ResponseEntity<ApiResponse<Object>> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeUnderWritingStatusRequest body
    ) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.changeStatus(id, body)));
    }

    @Operation(summary = "거절 사유 목록", description = "거절 사유 코드 목록 조회")
    @GetMapping("/reject-reasons")
    public ResponseEntity<ApiResponse<Object>> getRejectReasons() {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "서류 보완 요청", description = "거절사유가 서류누락일 때 필요 서류 보완 요청 전송")
    @PostMapping("/cases/{id}/documents/request")
    public ResponseEntity<ApiResponse<String>> requestDocuments(
            @PathVariable Long id,
            @RequestBody(required = false) DocumentRequest body
    ) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.requestDocuments(id, body)));
    }

    @Operation(summary = "금감원 전송 요청", description = "금감원(FSS)으로 계약 내용을 전송 요청")
    @PostMapping("/cases/{id}/fss/send")
    public ResponseEntity<ApiResponse<FssSendResponse>> sendToFss(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.sendToFss(id)));
    }

    @Operation(summary = "금감원 전송 상태 조회", description = "금감원 전송 처리 상태 조회")
    @GetMapping("/cases/{id}/fss/status")
    public ResponseEntity<ApiResponse<FssStatusResponse>> getFssStatus(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(underWritingService.getFssStatus(id)));
    }
}
