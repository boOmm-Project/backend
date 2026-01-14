package com.nuclear.boomm.product.controller;

import com.nuclear.boomm.common.ApiResponse;
import com.nuclear.boomm.product.dto.request.wrapper.ProductCoverageRequest;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageFileResponse;
import com.nuclear.boomm.product.dto.response.wrapper.ProductCoverageResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "상품", description = "상품 생성, 상품 개발, 상품 조회, 상품 출시에 대한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 생성", description = "해당 사용자의 고유번호로 새로운 상품 생성")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct() {
        // 권한: 상품 관리자
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(productService.createProduct(userId)));
    }

    @Operation(summary = "상품 저장", description = "개발 중인 상품을 완료 상태로 저장 혹은 임시 저장")
    @PostMapping(value = "/{product-id}/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductCoverageResponse>> saveProduct(
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("request") ProductCoverageRequest request,
            @RequestPart("file") List<MultipartFile> files,
            @PathVariable("product-id") Long productId
    ) {
        // 권한: 상품 관리자
        Long userId = 1L;

        return ResponseEntity.ok(ApiResponse.success(productService.save(userId, request, files, productId)));
    }

    @Operation(summary = "출시 상품 조회", description = "출시된 상품 목록 전체 조회")
    @GetMapping("/released")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getReleasedProducts() {
        // 권한: 상품 관리자, 이해 관계자, 컴플라이언스, 일반 사용자

        return ResponseEntity.ok(ApiResponse.success(productService.getReleasedProducts()));
    }

    @Operation(summary = "출시 전 상품 조회", description = "출시되지 않은 상품 목록 전체 조회")
    @GetMapping("/not-released")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getNotReleasedProducts() {
        // 권한: 상품 관리자, 이해 관계자, 컴플라이언스

        return ResponseEntity.ok(ApiResponse.success(productService.getNotReleasedProducts()));
    }

    @Operation(summary = "출시 상품 상세 조회", description = "출시된 상품에 대한 정보 상세 조회")
    @GetMapping("/{product-id}/details/released")
    public ResponseEntity<ApiResponse<ProductCoverageFileResponse>> getProductDetails(
            @PathVariable("product-id") Long productId
    ) {
        // 권한: 상품 관리자, 이해 관계자, 컴플라이언스, 일반 사용자

        return ResponseEntity.ok(ApiResponse.success(productService.getProductDetails(productId)));
    }

    @Operation(summary = "출시 전 상품 상세 조회", description = "출시 전 상품에 대한 정보 상세 조회")
    @GetMapping("/{product-id}/details/un-released")
    public ResponseEntity<ApiResponse<ProductResponse>> getUnReleasedProductDetails(
            @PathVariable("product-id") Long productId
    ) {
        // 권한: 상품 관리자, 이해 관계자, 컴플라이언스

        return ResponseEntity.ok(ApiResponse.success(productService.getUnReleasedProductDetails(productId)));
    }

    @Operation(summary = "출시 전 상품 삭제", description = "상태가 출시 전인 상품 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteProduct(
            @PathVariable("id") Long productId
    ) {
        // 권한: 컴플라이언스

        return ResponseEntity.ok(ApiResponse.success(productService.deleteUnReleasedProduct(productId)));
    }
}
