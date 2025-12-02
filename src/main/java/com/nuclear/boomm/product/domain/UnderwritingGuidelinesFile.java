package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnderwritingGuidelinesFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bucketName;

    @Column(nullable = false,  unique = true)
    private String filePath;   // url

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String fileName;

    private String extension;   // 확장자

    @Column(nullable = false)   // MIME 타입
    private String mimeType;

    @Column(nullable = false)
    private Long uploaderId;

    @Column(nullable = false)
    private Long systemRegPrepId;

    @Builder
    public UnderwritingGuidelinesFile(String bucketName, String filePath, String originalFileName, String fileName, String extension, String mimeType, Long uploaderId, Long systemRegPrepId) {
        this.bucketName = bucketName;
        this.filePath = filePath;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.extension = extension;
        this.mimeType = mimeType;
        this.uploaderId = uploaderId;
        this.systemRegPrepId = systemRegPrepId;
    }
}
