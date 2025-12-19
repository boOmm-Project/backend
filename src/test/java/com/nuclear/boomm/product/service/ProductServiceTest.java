package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.dto.request.ProductRequest;
import com.nuclear.boomm.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserDetails userDetails;

    @Test
    @DisplayName("상품 생성 시 userId 저장 및 productId 반환")
    void createProduct_VerifyId() {
        // given
        // 상품 생성자 id
        Long userId = 1L;
        // 상품 id
        Long productId = 10L;

        // username 설정
        given(userDetails.getUsername()).willReturn(String.valueOf(userId));

        // 생성할 product의 id 및 userId 설정
        Product product = Product.builder()
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(product,  "productId", productId);

        // productRepository가 저장한 userId를 반환하도록 설정
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.createProduct(userDetails);

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
        // username 설정
        given(userDetails.getUsername()).willReturn(String.valueOf(userId));

        Product product = Product.builder()
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(product,  "productId", productId);

        // productRepository
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.createProduct(userDetails);

        // then
        assertEquals("임시 상품", product.getProductName());
        assertEquals("임시 고객", product.getTargetCustomer());
        assertEquals("임시 판매 채널", product.getSalesChannel());
        assertFalse(product.isDone());
    }

    @Test
    @DisplayName("상품 임시 저장 시 값이 올바르게 저장되는지 확인")
    void draftProduct() {
        // given
        Long userId = 1L;
        Long productId = 10L;

        ProductRequest request = new ProductRequest(
                productId,
                "테스트 상품 이름",
                3L,
                "테스트 겨냥 고객",
                18,
                "테스트 판매 채널",
                userId,
                false
        );

        given(userDetails.getUsername()).willReturn(String.valueOf(userId));

        Product product = Product.builder()
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(product,  "productId", productId);

        given(productRepository.findByUserIdAndProductId(userId, productId)).willReturn(Optional.of(product));

        // when
        productService.save(userDetails, request);

        // then
        assertEquals(productId, product.getProductId());
        assertEquals(userId, product.getUserId());
        assertEquals(request.category(), product.getCategory());
        assertEquals(request.targetCustomer(), product.getTargetCustomer());
        assertEquals(request.salesChannel(), product.getSalesChannel());
        assertEquals(request.period(), product.getPeriod());
        assertFalse(product.isDone());
    }

    @Test
    @DisplayName("상품 최종 저장 시 isDone 값이 변경되는지 확인")
    void saveProduct() {
        // given
        Long userId = 1L;
        Long productId = 10L;

        ProductRequest request = new ProductRequest(
                productId,
                "테스트 상품 이름",
                3L,
                "테스트 겨냥 고객",
                18,
                "테스트 판매 채널",
                userId,
                true
        );

        given(userDetails.getUsername()).willReturn(String.valueOf(userId));

        Product product = Product.builder()
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(product,  "productId", productId);

        given(productRepository.findByUserIdAndProductId(userId, productId)).willReturn(Optional.of(product));

        // when
        productService.save(userDetails, request);

        // then
        assertTrue(product.isDone());
    }
}