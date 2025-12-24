package com.nuclear.boomm.product.controller;

import com.nuclear.boomm.product.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 피드백", description = "피드백 업데이트, 피드백에 반영, 피드백 추가 설명 요청 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedback")
@RestController
public class FeedbackController {

    private final FeedbackService feedbackService;

//    @Operation(description = "이해관계자의 상품에 대한 피드백 업데이트")
//    @GetMapping("/update")
//    public ResponseEntity<ApiResponse<FeedbackResponse>> update(
//            @RequestBody FeedbackRequest request,
//            @AuthenticationPrincipal UserDetails userDetails
//            ) {
//
//        return ResponseEntity.ok(ApiResponse.success(feedbackService.updateFeedback(userDetails, request)));
//    }

//    @Operation(description = "상품 관리자의 피드백에 대한 추가 설명 요청")
//    @GetMapping("/")
//
//    @Operation(description = "상품 관리자의 피드백 반영")
}
