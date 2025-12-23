package com.nuclear.boomm.product.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageRequest;
import com.nuclear.boomm.product.dto.response.ProductResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageFileResponse;
import com.nuclear.boomm.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "상품", description = "상품 생성, 상품 개발, 상품 조회, 상품 출시에 대한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Operation(description = "상품 생성")
//    @PreAuthorize("hasRole('PRODUCT_DEVELOPER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Long>> createProduct(
//            @AuthenticationPrincipal UserDetails userDetails
            ) {
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(productService.createProduct(userId)));
    }

    // minio에 어떻게 저장하고 그 결과를 DB에 저장할 지 로직 필요
    @Operation(description = "개발 중인 상품 저장 또는 임시저장")
//    @PreAuthorize("hasRole('PRODUCT_DEVELOPER')")
    @PutMapping("/save")
    public ResponseEntity<ApiResponse<ProductResponse>> saveProduct(
            @RequestBody ProductCoverageFileRequest request
//            @AuthenticationPrincipal UserDetails userDetails
            ) {
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(productService.save(userId, request)));
    }

    @Operation(description = "출시된 상품 목록 조회")
    @GetMapping("/released")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getReleasedProducts() {

        return ResponseEntity.ok(ApiResponse.success(productService.getReleasedProducts()));
    }

    @Operation(description = "개발 중 상품 목록 조회")
//    @PreAuthorize("hasAnyRole('PRODUCT_DEVELOPER', 'PRODUCT_STAKEHOLDER', 'PRODUCT_COMPLIANCE')")
    @GetMapping("/not-released")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getNotReleasedProducts() {

        return ResponseEntity.ok(ApiResponse.success(productService.getNotReleasedProducts()));
    }

    @Operation(description = "출시 상품 상세 조회")
    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse<ProductCoverageFileResponse>> getProductDetails(@PathVariable("id") Long productId) {

        return ResponseEntity.ok(ApiResponse.success(productService.getProductDetails(productId)));
    }
}
