package com.nuclear.boomm.underwriting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reex")
public class ReexaminationController {

    @GetMapping("/dashboard") // 재심사 대시보드
    public ResponseEntity<?> getDashboard(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return ResponseEntity.ok(null);

    }

    @GetMapping("/cases") // 재심사 목록 조회
    public ResponseEntity<?> getReexaminationCases(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/start") // 재심사 시작
    public ResponseEntity<?> startReexamination(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/complete") //재심사 완료
    public ResponseEntity<?> completeReexamination(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cases/{id}/reject") //재심사 거절
    public ResponseEntity<?> rejectReexamination(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/reject-reasons") //재심사 거절 사유
    public ResponseEntity<?> getRejectReasons() {
        return ResponseEntity.ok(null);
    }

}

