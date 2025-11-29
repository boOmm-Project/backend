package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.contract.enums.DocumentType;
import com.nuclear.boomm.contract.enums.FileType;
import com.nuclear.boomm.contract.enums.ProcessingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="customer_document")
@NoArgsConstructor
public class CustomerDocument extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long userId;   // fk

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private FileType fileType;

    @Column(nullable = false)
    private String filePath;

    @Column(columnDefinition = "text")
    private String documentRemark;

    private ProcessingStatus processingStatus;
}
