package com.nuclear.boomm.underwriting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/losss-ratio")
public class LossRatioController {

    @GetMapping("/form") // 손해율 분석 초기화면
    public ResponseEntity<?> getForm() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/analyze") // 손해율 분석 실행
    public ResponseEntity<?> analyzeLossRatio(
            @RequestBody(required = false) Object body
    ) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/context") // 손해율 분석에 필요한 고객 정보 조회 기본 데이터 묶음
    public ResponseEntity<?> getAnalysisContext(
            @RequestParam(required = false) Long underwritingReviewId,
            @RequestParam(required = false) Long contractId
    ) {
        return ResponseEntity.ok(null);
    }

}
