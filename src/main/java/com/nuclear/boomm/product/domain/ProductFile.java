package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.dto.response.ProductFileResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "product_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Builder.Default
    @Column(nullable = false)
    private String bucketName = "product-file";

    @Column(nullable = false, unique = true)
    private String url;   // url

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String uuidName;

    private String extension;   // 확장자

    @Column(nullable = false)   // MIME 타입
    private String contentType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private Long uploaderId;

    @Column(nullable = false)
    private Long productId;

    public static ProductFileResponse from(ProductFile productFile) {
        return new ProductFileResponse(
                productFile.getUrl(),
                productFile.getOriginalFilename()
        );
    }

    public static List<ProductFileResponse> from(List<ProductFile> productFile) {
        return productFile.stream()
                .map(ProductFile::from)
                .toList();
    }
}
