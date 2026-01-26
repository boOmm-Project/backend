package com.nuclear.boomm.underwriting.controller;

import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/uw")
public class UnderWritingController {

    @GetMapping("/dashboard") // 인수심사 대시보드
    public ResponseEntity<?> getDashboard(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    )
    {
        return ResponseEntity.ok(null);

    }

    @GetMapping("/cases") // 인수심사 케이스 목록
    public ResponseEntity<?> getCases(
            @RequestParam(required = false) UnderWritingStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(null);

    }

    @GetMapping("/cases/{id}") // 인수심사 케이스 상세
    public ResponseEntity<?> getCaseDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/assign") // 담당자 할당
    public ResponseEntity<?> assignUnderwritier(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/start") // 심사시작

    public ResponseEntity<?> startUnderwriting(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("cases/{id}/approve") // 승인
    public ResponseEntity<?> approve(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/reject") // 거절
    public ResponseEntity<?> reject(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/reject-reasons") // 거절사유 목록
    public ResponseEntity<?> getrejectReasons() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/documents/request") // 서류 보완 요청
    public ResponseEntity<?> requestDocument(
            @PathVariable Long id,
            @RequestBody(required = false) Object body
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/fss/send") // 금감원 전송 요청
    public ResponseEntity<?> sendToFss(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/cases/{id}/fss/status") // 금감원 전송 상태 조회
    public ResponseEntity<?> getFssStatus(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(null);
    }






}
