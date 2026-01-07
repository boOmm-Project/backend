package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.ExtraDescription;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackExtraDescriptionRequest;
import com.nuclear.boomm.product.dto.request.feedback.FeedbackUpdateRequest;
import com.nuclear.boomm.product.dto.request.product.ProductRequest;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackExtraDescriptionResponse;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.feedback.FeedbackRepository;
import com.nuclear.boomm.product.repository.product.ExtraDescriptionRepository;
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
    @Mock
    private ExtraDescriptionRepository extraDescriptionRepository;


    private Long stakeholderId;
    private Long productManagerId;

    private Long productId;
    private Long feedbackId;

    private Feedback feedback;
    private FeedbackUpdateRequest updateRequest;

    private Product product;
    private ProductRequest productRequest;
    private ProductRequest releasedProductRequest;

    private ExtraDescription extraDescription;
    private Long extraDescriptionId;
    private FeedbackExtraDescriptionRequest feedbackExtraDescriptionRequest;

    @BeforeEach
    void setUp() {
        stakeholderId = 1L;
        productManagerId = 10L;

        productId = 2L;
        feedbackId = 20L;
        extraDescriptionId = 200L;

        product = Product.builder()
                .userId(productManagerId)
                .build();
        ReflectionTestUtils.setField(product, "productId", productId);

        feedback = Feedback.builder()
                .product(product)
                .writerId(stakeholderId)
                .build();
        ReflectionTestUtils.setField(feedback, "feedbackId", feedbackId);

        feedbackExtraDescriptionRequest = new FeedbackExtraDescriptionRequest(
                "reqDescription"
        );

        extraDescription = ExtraDescription.builder()
                .feedbackId(feedbackId)
                .productId(productId)
                .request(feedbackExtraDescriptionRequest.description())
                .creatorId(productManagerId)
                .build();
        ReflectionTestUtils.setField(extraDescription, "extraDescriptionId", extraDescriptionId);

        updateRequest = new FeedbackUpdateRequest(
                "reqDescription"
        );

        productRequest = new ProductRequest(
                productId,
                "changedProductName",
                1L,
                "changedTargetCustomer",
                12,
                "changedSalesChannel",
                productManagerId,
                true,
                stakeholderId,
                false
        );

        releasedProductRequest = new ProductRequest(
                productId,
                "changedProductName",
                1L,
                "changedTargetCustomer",
                12,
                "changedSalesChannel",
                productManagerId,
                true,
                stakeholderId,
                true
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
    @Test
    @DisplayName("상품 관리자 상품 피드백 조회 - 실패 - PRODUCT_NOT_FOUND")
    void getAllProductManagerFeedbacks_Failure_PRODUCT_NOT_FOUND() {
        // given
        given(productRepository.existsByUserId(productManagerId)).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class,
                () -> feedbackService.getProductManagerFeedback(productManagerId));
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }


    @Test
    @DisplayName("피드백 추가 설명 요청 - 성공")
    void requestExtraDescription_Success() {
        // given
        given(feedbackRepository.existsByFeedbackId(feedbackId)).willReturn(true);

        given(productRepository.existsByProductIdAndUserId(productId, productManagerId)).willReturn(true);

        given(extraDescriptionRepository.save(any(ExtraDescription.class))).willAnswer(invocation -> extraDescription);

        // when
        FeedbackExtraDescriptionResponse response = feedbackService.requestExtraDescription(productManagerId, feedbackId, productId, feedbackExtraDescriptionRequest);

        // then
        assertEquals(feedbackId, response.feedbackId());
        assertEquals(productManagerId, response.constructor());
        assertEquals(productId, response.productId());
        assertEquals(feedbackExtraDescriptionRequest.description(), response.request());
        assertEquals("추가 설명을 입력해 주세요.", response.response());
    }
    @Test
    @DisplayName("피드백 추가 설명 요청 - 실패 - FEEDBACK_NOT_FOUND")
    void requestExtraDescription_Failure_FEEDBACK_NOT_FOUND() {
        // given
        given(feedbackRepository.existsByFeedbackId(feedbackId)).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class,
                () -> feedbackService.requestExtraDescription(productManagerId, feedbackId, productId, feedbackExtraDescriptionRequest)
        );
        assertEquals(ErrorCode.FEEDBACK_NOT_FOUND, exception.getErrorCode());
    }
    @Test
    @DisplayName("피드백 추가 설명 요청 - 실패 - PRODUCT_NOT_FOUND")
    void requestExtraDescription_Failure_PRODUCT_NOT_FOUND() {
        // given
        given(feedbackRepository.existsByFeedbackId(feedbackId)).willReturn(true);

        given(productRepository.existsByProductIdAndUserId(productId, productManagerId)).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class,
                () -> feedbackService.requestExtraDescription(productManagerId, feedbackId, productId, feedbackExtraDescriptionRequest)
        );
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }


    @Test
    @DisplayName("피드백 추가 설명 전송 - 성공")
    void responseExtraDescription_Success() {
        // given
        given(feedbackRepository.existsByFeedbackIdAndWriterId(feedbackId, stakeholderId)).willReturn(true);

        given(extraDescriptionRepository.findByExtraDescriptionId(extraDescriptionId)).willReturn(Optional.of(extraDescription));

        // when
        FeedbackExtraDescriptionResponse response = feedbackService.responseExtraDescription(stakeholderId, extraDescriptionId, feedbackExtraDescriptionRequest);

        // then
        assertEquals(feedbackId, response.feedbackId());
        assertEquals(productManagerId, response.constructor());
        assertEquals(productId, response.productId());
        assertEquals(feedbackExtraDescriptionRequest.description(), response.request());
    }
    @Test
    @DisplayName("피드백 추가 설명 전송 - 실패 - EXTRA_DESCRIPTION_NOT_FOUND")
    void responseExtraDescription_Failure_EXTRA_DESCRIPTION_NOT_FOUND() {
        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> feedbackService.responseExtraDescription(stakeholderId, feedbackId, feedbackExtraDescriptionRequest));
        assertEquals(ErrorCode.EXTRA_DESCRIPTION_NOT_FOUND, exception.getErrorCode());
    }


    @Test
    @DisplayName("피드백 반영 - 성공")
    void reflectFeedback_Success() {
        // given
        given(productRepository.findByProductIdAndUserId(productId, productManagerId)).willReturn(Optional.of(product));

        given(feedbackRepository.findByFeedbackIdAndProduct_ProductId(feedbackId, productId)).willReturn(Optional.of(feedback));

        // when
        ProductResponse response = feedbackService.reflectFeedback(productManagerId, feedbackId, productId, productRequest);

        // then
        assertEquals(productId, response.productId());
        assertEquals("changedProductName", response.productName());
        assertEquals(1L, response.category());
        assertEquals("changedTargetCustomer", response.targetCustomer());
        assertEquals(12, response.period());
        assertEquals("changedSalesChannel",  response.salesChannel());
        assertEquals(productManagerId, response.userId());
        assertEquals(true, response.isDone());
        assertEquals(false, response.isReleased());
    }
    @Test
    @DisplayName("피드백 반영 - 실패 - PRODUCT_IS_RELEASED")
    void reflectFeedback_Failure_PRODUCT_IS_RELEASED() {
        // given
        given(productRepository.findByProductIdAndUserId(productId, productManagerId)).willReturn(Optional.of(product));

        given(feedbackRepository.findByFeedbackIdAndProduct_ProductId(feedbackId, productId)).willReturn(Optional.of(feedback));

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> feedbackService.reflectFeedback(productManagerId, feedbackId, productId, releasedProductRequest));
        assertEquals(ErrorCode.PRODUCT_IS_RELEASED, exception.getErrorCode());
    }
}