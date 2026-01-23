package com.nuclear.boomm.product.service;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.dto.request.product.CoverageRequest;
import com.nuclear.boomm.product.dto.request.product.ProductRequest;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageRequest;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageFileResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageResponse;
import com.nuclear.boomm.product.enums.CoverageCategory;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.repository.feedback.FeedbackRepository;
import com.nuclear.boomm.product.repository.product.CoverageRepository;
import com.nuclear.boomm.product.repository.product.ProductFileRepository;
import com.nuclear.boomm.product.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
    @Mock
    private FileService fileService;
    //    @Mock
//    private UserDetails userDetails;
    @Captor
    private ArgumentCaptor<List<ProductFile>> productFilesCaptor;
    @Captor
    private ArgumentCaptor<List<Coverage>> coveragesCaptor;

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
                "상품",
                3L,
                "고객",
                12,
                "채널",
                userId,
                true,
                stakeholderId,
                false
        );

        request = new ProductCoverageRequest(
                productRequest,
                List.of(
                        new CoverageRequest(
                                1L,
                                CoverageCategory.MANDATORY_BASIC_COVERAGE,
                                productId,
                                "제목",
                                "상세설명",
                                5.0,
                                10.0,
                                true,
                                "피해산정기준"
                        ),
                        new CoverageRequest(
                                2L,
                                CoverageCategory.MANDATORY_BASIC_COVERAGE,
                                productId,
                                "제목",
                                "상세설명",
                                5.0,
                                10.0,
                                true,
                                "피해산정기준"
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
                .coverageId(1L)
                .build();
        cov2 = Coverage.builder()
                .productId(productId)
                .coverageId(2L)
                .build();
        coverageList = List.of(cov1, cov2);

        doneProductList = new ArrayList<>(List.of(
                Product.builder()
                        .userId(1L)
                        .isDone(true)
                        .isReleased(true)
                        .build()
                ,
                Product.builder()
                        .userId(2L)
                        .isDone(true)
                        .isReleased(true)
                        .build()
                ,
                Product.builder()
                        .userId(1L)
                        .isDone(true)
                        .isReleased(true)
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

        multipartFileList = List.of(
                new MultipartFile() {
                    @Override
                    public String getName() {
                        return "";
                    }

                    @Override
                    public String getOriginalFilename() {
                        return "";
                    }

                    @Override
                    public String getContentType() {
                        return "";
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public long getSize() {
                        return 0;
                    }

                    @Override
                    public byte[] getBytes() throws IOException {
                        return new byte[0];
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return new InputStream() {
                            @Override
                            public int read() throws IOException {
                                return 0;
                            }
                        };
                    }

                    @Override
                    public void transferTo(File dest) throws IOException, IllegalStateException {

                    }
                }
        );
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

    @Test
    @DisplayName("상품 임시 저장 시 isDone 값이 변경되지 않는지 확인")
    void saveProduct() {
        // given
        ProductRequest proReq = new ProductRequest(
                "새상품명",
                1L,
                "새고객",
                12,
                "새채널",
                userId,
                false,
                stakeholderId,
                false
        );

        List<CoverageRequest> covReq = List.of(
                new CoverageRequest(
                        cov1.getCoverageId(),
                        CoverageCategory.MANDATORY_BASIC_COVERAGE,
                        productId,
                        "새담보명",
                        "새설명",
                        0.3,
                        0.4,
                        false,
                        "새 피해산정기준"
                ),
                new CoverageRequest(
                        cov2.getCoverageId(),
                        CoverageCategory.MANDATORY_BASIC_COVERAGE,
                        productId,
                        "새담보명4L",
                        "새설명",
                        0.3,
                        0.4,
                        false,
                        "새 피해산정기준"
                ));

        ProductCoverageRequest proCovReq = new ProductCoverageRequest(
                proReq,
                covReq
        );

        multipartFileList = List.of(
                new MockMultipartFile("files", "test.jpg", "image/png", "content".getBytes())
        );

        given(coverageRepository.findAllByProductId(productId))
                .willReturn(List.of(cov1, cov2));

        given(productRepository.findByProductIdAndUserId(productId, userId))
                .willReturn(Optional.of(product));

        // when
        ProductCoverageResponse response = productService.save(userId, proCovReq, multipartFileList, productId);

        // then
        assertEquals("새상품명", response.product().productName());
        assertFalse(response.product().isDone());
        assertEquals(productId, response.product().productId());

        assertEquals(userId, response.product().userId());

        assertEquals(2, response.coverage().size());
        assertEquals(1L, response.coverage().get(0).id());
        assertEquals("새담보명4L", response.coverage().get(1).title());

        verify(productFileRepository).deleteByProductId(productId);

        verify(feedbackRepository, never()).save(any(Feedback.class));
    }

    @Test
    @DisplayName("상품 최종 저장 시 isDone 값 변경 확인")
    void draftProduct() {
        // given
        ProductRequest proReq = new ProductRequest(
                "새상품명",
                1L,
                "새고객",
                12,
                "새채널",
                userId,
                true,
                stakeholderId,
                false
        );

        List<CoverageRequest> covReq = List.of(
                new CoverageRequest(
                        cov1.getCoverageId(),
                        CoverageCategory.MANDATORY_BASIC_COVERAGE,
                        productId,
                        "새담보명",
                        "새설명",
                        0.3,
                        0.4,
                        false,
                        "새 피해산정기준"
                ),
                new CoverageRequest(
                        cov2.getCoverageId(),
                        CoverageCategory.MANDATORY_BASIC_COVERAGE,
                        productId,
                        "새담보명4L",
                        "새설명",
                        0.3,
                        0.4,
                        false,
                        "새 피해산정기준"
                ));

        ProductCoverageRequest proCovReq = new ProductCoverageRequest(
                proReq,
                covReq
        );

        multipartFileList = List.of(
                new MockMultipartFile("files", "test.jpg", "image/png", "content".getBytes())
        );

        given(coverageRepository.findAllByProductId(productId))
                .willReturn(List.of(cov1, cov2));

        given(productRepository.findByProductIdAndUserId(productId, userId))
                .willReturn(Optional.of(product));

        // when
        ProductCoverageResponse response = productService.save(userId, proCovReq, multipartFileList, productId);

        // then
        assertEquals("새상품명", response.product().productName());
        assertTrue(response.product().isDone());
        assertEquals(productId, response.product().productId());

        assertEquals(userId, response.product().userId());

        assertEquals(1L, response.coverage().get(0).id());
        assertEquals(2, response.coverage().size());
        assertEquals("새담보명", response.coverage().get(0).title());
        assertEquals("새담보명4L", response.coverage().get(1).title());

        verify(productFileRepository).deleteByProductId(productId);
    }

    @Test
    @DisplayName("출시 상태의 모든 상품 조회")
    void selectDoneProducts() {
        // given
        given(productRepository.findAllByIsReleasedTrue()).willReturn(doneProductList);

        // when
        List<ProductResponse> responses = productService.getReleasedProducts();

        // then
        verify(productRepository, times(1)).findAllByIsReleasedTrue();

        assertTrue(responses.stream().allMatch(ProductResponse::isReleased));
    }

    @Test
    @DisplayName("미출시 상태의 모든 상품 조회")
    void selectNotDoneProducts() {
        // given
        given(productRepository.findAllByIsReleasedFalse()).willReturn(notDoneProductList);

        // when
        List<ProductResponse> responses = productService.getNotReleasedProducts();

        // then
        verify(productRepository, times(1)).findAllByIsReleasedFalse();

        assertFalse(responses.stream().allMatch(ProductResponse::isDone));
    }

    @Test
    @DisplayName("상품 하나의 상세 정보 전달")
    void selectProductDetails() {
        // given
        Product mockProduct = Product.builder()
                .userId(userId)
                .isDone(true)
                .isReleased(true)
                .build();
        ReflectionTestUtils.setField(mockProduct, "productId", productId);

        given(productRepository.findByProductIdAndIsReleasedTrue(productId)).willReturn(Optional.of(mockProduct));
        given(productFileRepository.findAllByProductId(productId)).willReturn(productFileList);
        given(coverageRepository.findAllByProductId(productId)).willReturn(coverageList);

        // when
        ProductCoverageFileResponse response = productService.getProductDetails(productId);

        // then
        verify(productRepository, times(1)).findByProductIdAndIsReleasedTrue(productId);
        verify(productFileRepository, times(1)).findAllByProductId(productId);
        verify(coverageRepository, times(1)).findAllByProductId(productId);

        assertNotNull(response);
        assertEquals(productId, response.productResponse().productId());
        assertEquals(userId, response.productResponse().userId());

        assertEquals(1L, response.coverageResponses().get(0).id());
        assertEquals(2L, response.coverageResponses().get(1).id());
    }


    @Test
    @DisplayName("출시 전 상품 상세조회 - 성공")
    void getUnReleasedProductDetails_Success() {
        // given
        given(productRepository.findByProductIdAndIsReleasedFalse(productId)).willReturn(Optional.of(product));

        // when
        ProductCoverageFileResponse response = productService.getUnReleasedProductDetails(productId);

        // then
        assertEquals(productId, response.productResponse().productId());
    }
    @Test
    @DisplayName("출시 전 상품 상세조회 - 실패 - PRODUCT_NOT_FOUND")
    void getUnReleasedProductDetails_Failure_PRODUCT_NOT_FOUND() {
        // given
        given(productRepository.findByProductIdAndIsReleasedFalse(productId)).willReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () ->
                productService.getUnReleasedProductDetails(productId)
        );
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());

        verify(productRepository, times(1)).findByProductIdAndIsReleasedFalse(productId);
    }
}