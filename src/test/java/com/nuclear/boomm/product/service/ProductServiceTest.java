package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.dto.request.CoverageRequest;
import com.nuclear.boomm.product.dto.request.ProductRequest;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageRequest;
import com.nuclear.boomm.product.dto.response.ProductResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageFileResponse;
import com.nuclear.boomm.product.repository.CoverageRepository;
import com.nuclear.boomm.product.repository.FeedbackRepository;
import com.nuclear.boomm.product.repository.ProductFileRepository;
import com.nuclear.boomm.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductFileRepository productFileRepository;
    @Mock
    private CoverageRepository coverageRepository;
    @Mock
    private FeedbackRepository feedbackRepository;
//    @Mock
//    private UserDetails userDetails;
//    @Captor
//    private ArgumentCaptor<List<ProductFile>> productFilesCaptor;
//    @Captor
//    private ArgumentCaptor<List<Coverage>> coveragesCaptor;

    private ProductRequest productRequest;
    private ProductCoverageRequest request;
    private Product product;
    private ProductFile pf1;
    private ProductFile pf2;
    private Coverage cov1;
    private Coverage cov2;
    private Long userId;
    private Long productId;
    private Long stakeholderId;
    private List<Product> doneProductList;  // DB에 있는 상품 목록
    private List<Product> notDoneProductList;
    private List<ProductFile> productFileList;
    private List<Coverage> coverageList;
    private List<MultipartFile> multipartFileList;

    private String username;

    @BeforeEach
    void setUp() {
        userId = 1L;
        productId = 10L;
        stakeholderId = 99L;
        username = "username";
//        given(userDetails.getUsername()).willReturn(String.valueOf(userId));

        // request
        productRequest = new ProductRequest(
                productId,
                "상품",
                3L,
                "고객",
                12,
                "채널",
                userId,
                true,
                stakeholderId
        );

        request = new ProductCoverageRequest(
                productRequest,
                List.of(
                        new CoverageRequest(
                                "제목",
                                "상세설명",
                                5.0,
                                10.0,
                                true
                        ),
                        new CoverageRequest(
                                "제목",
                                "상세설명",
                                5.0,
                                10.0,
                                true
                        )
                )
        );

        product = Product.builder()
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(product, "productId", productId);

        pf1 = ProductFile.builder()
                .productId(productId)
                .build();
        pf2 = ProductFile.builder()
                .productId(productId)
                .build();
        productFileList = List.of(pf1, pf2);

        cov1 = Coverage.builder()
                .productId(productId)
                .build();
        cov2 = Coverage.builder()
                .productId(productId)
                .build();
        coverageList = List.of(cov1, cov2);

        doneProductList = new ArrayList<>(List.of(
                Product.builder()
                        .userId(1L)
                        .isDone(true)
                        .build()
                ,
                Product.builder()
                        .userId(2L)
                        .isDone(true)
                        .build()
                ,
                Product.builder()
                        .userId(1L)
                        .isDone(true)
                        .build()
        ));

        notDoneProductList = new ArrayList<>(List.of(
                Product.builder()
                        .userId(1L)
                        .isDone(false)
                        .build()
                ,
                Product.builder()
                        .userId(2L)
                        .isDone(false)
                        .build()
                ,
                Product.builder()
                        .userId(1L)
                        .isDone(false)
                        .build()
        ));
    }

    @Test
    @DisplayName("상품 생성 시 userId 저장 및 productId 반환")
    void createProduct_VerifyId() {
        // given
        // 상품 생성자 id
        Long userId = 1L;
        // 상품 id
        Long productId = 10L;

        // 생성할 product의 id 및 userId 설정
        Product product = Product.builder()
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(product, "productId", productId);

        // productRepository가 저장한 userId를 반환하도록 설정
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.createProduct(userId);

        // then
        assertEquals(productId, product.getProductId());
        assertEquals(userId, product.getUserId());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 생성 시 기본 값이 들어가는지 확인")
    void createProduct_DefaultValue() {
        // given
        Long userId = 1L;
        Long productId = 10L;

        Product product = Product.builder()
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(product, "productId", productId);

        // productRepository
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.createProduct(userId);

        // then
        assertEquals("임시 상품", product.getProductName());
        assertEquals("임시 고객", product.getTargetCustomer());
        assertEquals("임시 판매 채널", product.getSalesChannel());
        assertFalse(product.isDone());
    }

    /*@Test
    @DisplayName("상품 임시 저장 시 담보, 파일은 삭제 후 새로운 값 저장")
    void draftProduct() {
        // given
        // request
        given(productRepository.findByProductIdAndUserId(productId, userId))
                .willReturn(Optional.of(product));

        given(productFileRepository.findAllByProductId(productId))
                .willReturn(List.of(pf1, pf2));

        given(coverageRepository.findAllByProductId(productId))
                .willReturn(List.of(cov1, cov2));

        // when
        ProductResponse productResponse = productService.save(userId, request);

        // then: 조회 여부
        verify(productRepository).findByProductIdAndUserId(productId, userId);

        // then: 기존 파일 delete 호출 여부
        verify(productFileRepository).delete(pf1);
        verify(productFileRepository).delete(pf2);
        verify(coverageRepository).delete(cov1);
        verify(coverageRepository).delete(cov2);

        // then: saveAll
        verify(productFileRepository).saveAll(productFilesCaptor.capture());
        verify(coverageRepository).saveAll(coveragesCaptor.capture());

        assertEquals(2, productFilesCaptor.getValue().size());
        assertEquals(2, coveragesCaptor.getValue().size());

        verify(feedbackRepository, never()).save(any(Feedback.class));

        // then: isDone의 false 유지
        assertFalse(product.isDone());

        // then: response가 null이 아닌지
        assertNotNull(productResponse);
    }*/

    /*@Test
    @DisplayName("상품 최종 저장 시 isDone 값이 변경되는지 확인")
    void saveProduct() {
        // given
        given(userDetails.getUsername()).willReturn(String.valueOf(userId));

        // request
        given(productRepository.findByProductIdAndUserId(productId, userId))
                .willReturn(Optional.of(product));

        given(productFileRepository.findAllByProductId(productId))
                .willReturn(List.of(pf1, pf2));

        given(coverageRepository.findAllByProductId(productId))
                .willReturn(List.of(cov1, cov2));

        Feedback feedback = Feedback.builder()
                .productId(product.getProductId())
                .writerId(request.stakeholderId())
                .build();

        given(feedbackRepository.save(any(Feedback.class))).willReturn(feedback);

        // when
        ProductResponse productResponse = productService.save(userDetails, request);

        // then: 조회 여부
        verify(productRepository).findByProductIdAndUserId(productId, userId);

        // then: 기존 파일 delete 호출 여부
        verify(productFileRepository).delete(pf1);
        verify(productFileRepository).delete(pf2);
        verify(coverageRepository).delete(cov1);
        verify(coverageRepository).delete(cov2);

        // then: saveAll
        verify(productFileRepository).saveAll(productFilesCaptor.capture());
        verify(coverageRepository).saveAll(coveragesCaptor.capture());

        assertEquals(2, productFilesCaptor.getValue().size());
        assertEquals(2, coveragesCaptor.getValue().size());

        verify(feedbackRepository, times(1)).save(any(Feedback.class));

        // then: isDone의 true
        assertTrue(product.isDone());

        // then: response가 null이 아닌지
        assertNotNull(productResponse);
    }*/

    @Test
    @DisplayName("출시 상태의 모든 상품 조회")
    void selectDoneProducts() {
        // given
        Long productId1 = 1L;
        Long productId2 = 2L;
        Long productId3 = 3L;

        given(productRepository.findAllByIsDoneTrue()).willReturn(doneProductList);

        // when
        List<ProductResponse> responses = productService.getReleasedProducts();

        // then
        verify(productRepository, times(1)).findAllByIsDoneTrue();

        assertTrue(responses.stream().allMatch(ProductResponse::isDone));
    }

    @Test
    @DisplayName("미출시 상태의 모든 상품 조회")
    void selectNotDoneProducts() {
        // given
        given(productRepository.findAllByIsDoneFalse()).willReturn(notDoneProductList);

        // when
        List<ProductResponse> responses = productService.getNotReleasedProducts();

        // then
        verify(productRepository, times(1)).findAllByIsDoneFalse();

        assertFalse(responses.stream().allMatch(ProductResponse::isDone));
    }

    @Test
    @DisplayName("상품 하나의 상세 정보 전달")
    void selectProductDetails() {
        // given
        given(productRepository.findByProductId(productId)).willReturn(Optional.of(product));
        given(productFileRepository.findAllByProductId(productId)).willReturn(productFileList);
        given(coverageRepository.findAllByProductId(productId)).willReturn(coverageList);

        // when
        ProductCoverageFileResponse response = productService.getProductDetails(productId);

        // then
        verify(productRepository, times(1)).findByProductId(productId);
        verify(productFileRepository, times(1)).findAllByProductId(productId);
        verify(coverageRepository, times(1)).findAllByProductId(productId);

        assertNotNull(response);
        assertEquals(productId, response.product().productId());
    }
}