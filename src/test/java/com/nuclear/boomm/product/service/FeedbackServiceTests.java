package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.FeedbackRepository;
import com.nuclear.boomm.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTests {
    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private FeedbackRepository feedbackRepository;


    private Long userId;
    private Long productId;
    private Long feedbackId;

    private Feedback feedback;

    @BeforeEach
    void setUp() {
        userId = 1L;
        productId = 10L;
        feedbackId = 100L;

        feedback = Feedback.builder()
                .productId(productId)
                .writerId(userId)
                .build();
        ReflectionTestUtils.setField(feedback, "feedbackId", feedbackId);
    }

    @Test
    @DisplayName("피드백 생성 - 성공")
    void createFeedback_Success() {
        // given
        given(productRepository.existsByProductId(productId)).willReturn(true);

        given(feedbackRepository.save(any(Feedback.class))).willAnswer(invocation -> feedback);

        // when
        FeedbackResponse response = feedbackService.createFeedback(userId, productId);

        // then
        assertEquals(productId, response.productId());
        assertEquals(userId, response.writerId());
    }
    @Test
    @DisplayName("피드백 생성 - 실패 - 잘못된 productId")
    void createFeedback_Failure() {
        // given
        given(productRepository.existsByProductId(productId)).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> feedbackService.createFeedback(userId, productId));
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }



}