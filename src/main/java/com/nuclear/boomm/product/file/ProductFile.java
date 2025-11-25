package com.nuclear.boomm.product.file;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(nullable = false)
    private String bucketName;

    @Column(nullable = false,  unique = true)
    private String objectKey;   // url

    @Column(nullable = false)
    private String originalFilename;

    private String extension;   // 확장자

    @Column(nullable = false)   // MIME 타입
    private String contentType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private Long uploaderId;

    @Builder(builderMethodName = "noExtensionBuilder")
    public ProductFile(String bucketName, String objectKey, String originalFilename, String contentType, Long fileSize, Long uploaderId) {
        this.bucketName = bucketName;
        this.objectKey = objectKey;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.uploaderId = uploaderId;
    }

    @Builder    // extension 필드 포함된 기본 builder
    public ProductFile(String bucketName, String objectKey, String originalFilename, String extension, String contentType, Long fileSize, Long uploaderId) {
        this.bucketName = bucketName;
        this.objectKey = objectKey;
        this.originalFilename = originalFilename;
        this.extension = extension;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.uploaderId = uploaderId;
    }
}
