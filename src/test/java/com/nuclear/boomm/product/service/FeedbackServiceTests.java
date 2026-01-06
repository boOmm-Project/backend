package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackUpdateRequest;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.feedback.FeedbackRepository;
import com.nuclear.boomm.product.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

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


    private Long stakeholderId;
    private Long productManagerId;

    private Long productId;
    private Long feedbackId;

    private Feedback feedback;
    private FeedbackUpdateRequest updateRequest;

    private Product product;

    @BeforeEach
    void setUp() {
        stakeholderId = 1L;
        productManagerId = 10L;

        productId = 2L;
        feedbackId = 20L;

        product = Product.builder()
                .userId(productManagerId)
                .build();
        ReflectionTestUtils.setField(product, "productId", productId);

        feedback = Feedback.builder()
                .product(product)
                .writerId(stakeholderId)
                .build();
        ReflectionTestUtils.setField(feedback, "feedbackId", feedbackId);

        updateRequest = new FeedbackUpdateRequest(
                "reqDescription"
        );
    }

    @Test
    @DisplayName("피드백 생성 - 성공")
    void createFeedback_Success() {
        // given
        given(productRepository.existsByProductId(productId)).willReturn(true);

        given(feedbackRepository.save(any(Feedback.class))).willAnswer(invocation -> feedback);

        // when
        FeedbackResponse response = feedbackService.createFeedback(stakeholderId, productId);

        // then
        assertEquals(productId, response.productId());
        assertEquals(stakeholderId, response.writerId());
    }
    @Test
    @DisplayName("피드백 생성 - 실패 - 잘못된 productId")
    void createFeedback_Failure_INVALID_PRODUCT_ID() {
        // given
        given(productRepository.existsByProductId(productId)).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> feedbackService.createFeedback(stakeholderId, productId));
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }


    @Test
    @DisplayName("이해관계자 피드백 업데이트 - 성공")
    void updateFeedback_Success() {
        // given
        given(feedbackRepository.findByFeedbackIdAndWriterId(feedbackId, stakeholderId)).willReturn(Optional.of(feedback));

        // when
        FeedbackResponse response = feedbackService.updateFeedback(stakeholderId, feedbackId, updateRequest);

        // then
        assertEquals(feedbackId, response.feedbackId());
        assertEquals(stakeholderId, response.writerId());
        assertEquals("reqDescription", response.description());
        assertEquals(FeedbackStatus.STAKEHOLDER_FEEDBACK_UPDATE_PENDING, response.status());
    }
    @Test
    @DisplayName("이해관계자 피드백 업데이트 - 실패 - INVALID INPUT")
    void updateFeedback_Failure_InvalidInput() {
        // given
        given(feedbackRepository.findByFeedbackIdAndWriterId(feedbackId, stakeholderId)).willReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> feedbackService.updateFeedback(stakeholderId, feedbackId, updateRequest));
        assertEquals(ErrorCode.INVALID_INPUT_VALUE, exception.getErrorCode());
    }


    @Test
    @DisplayName("이해관계자 피드백 전체 조회 - 성공")
    void getAllStakeholderFeedbacks_Success() {
        // given
        given(feedbackRepository.findAllByWriterId(stakeholderId)).willReturn(List.of(feedback));

        // when
        List<FeedbackResponse> responses = feedbackService.getAllStakeholderFeedbacks(stakeholderId);

        // then
        assertEquals(1, responses.size());
        assertEquals(feedbackId, responses.get(0).feedbackId());
        assertEquals(stakeholderId, responses.get(0).writerId());
    }


    @Test
    @DisplayName("상품 관리자 상품 피드백 조회 - 성공")
    void getAllProductManagerFeedbacks_Success() {
        // given
        given(productRepository.existsByUserId(productManagerId)).willReturn(true);

        given(feedbackRepository.searchAllFeedbackByUserIdWithProduct(productManagerId)).willReturn(List.of(feedback));

        // when
        List<FeedbackResponse> response = feedbackService.getProductManagerFeedback(productManagerId);

        // then
        assertEquals(productId, response.get(0).productId());
        assertEquals(stakeholderId, response.get(0).writerId());
        assertEquals(feedbackId, response.get(0).feedbackId());
        assertEquals("피드백 사항을 작성해 주세요.", response.get(0).description());
    }
}