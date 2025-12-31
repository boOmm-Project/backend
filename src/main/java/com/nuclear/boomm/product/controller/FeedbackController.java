package com.nuclear.boomm.product.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackRequest;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.List;

@Tag(name = "상품 피드백", description = "피드백 생성, 피드백 업데이트, 피드백 조회, 피드백 반영, 피드백 추가 설명 요청/전송 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedbacks")
@RestController
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "피드백 생성", description = "이해관계자를 지정해 상품에 대한 피드백 생성")
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<FeedbackResponse>> createFeedback(
            @PathVariable("id") Long productId
    ) {
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(feedbackService.createFeedback(userId, productId)));
    }

    @Operation(summary = "이해관계자의 피드백 업데이트", description = "이해관계자의 요청받은 상품에 대한 피드백 업데이트")
    @PatchMapping("/{id}/content")
    public ResponseEntity<ApiResponse<FeedbackResponse>> updateFeedback(
            @RequestBody FeedbackRequest request
            ) {
        Long userId = 1L;

        return null;
    }

    @Operation(summary = "이해관계자의 피드백 조회", description = "이해관계자가 요청받은 피드백 완료 여부 상관 없이 전부 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getAllStakeholderFeedbacks(
            @RequestParam(required = false) String role
    ) {
        return null;
    }

    @Operation(summary = "상품 관리자의 피드백 조회", description = "피드백 완료 여부 상관 없이 상품 관리자의 해당 상품에 대한 피드백 조회")
    @GetMapping("/product-manager")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getAllProductManagerFeedbacks(
            @RequestParam(required = false) String role
    ) {
        return null;
    }

    @Operation(summary = "피드백에 대한 추가 설명 요청", description = "상품 관리자의 피드백에 대한 추가 설명 요청")
    @PostMapping("/{id}/description-requests")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> requestExtraDescriptions() {
        return null;
    }

    @Operation(summary = "피드백에 대한 추가 설명 전송", description = "피드백에 대해 요청받은 추가 설명 전송")
    @PostMapping("/{id}/descriptions")
    public ResponseEntity<ApiResponse<FeedbackResponse>> getExtraDescriptions() {
        return null;
    }

    @Operation(summary = "상품 관리자의 피드백 반영", description = "상품 관리자가 요청받은 피드백을 반영")
    @PatchMapping("/{id}/reflection")
    public ResponseEntity<ApiResponse<FeedbackResponse>> reflectFeedback() {
        return null;
    }
}
