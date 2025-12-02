package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.common.BaseEntity;
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
@Table(name="customer_file")
@NoArgsConstructor
public class CustomerFile extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long userId;   // fk

    @Column(nullable = false)
    private String fileName;        // 파일 이름

    @Column(nullable = false)
    private String filePath;        // 파일 경로

    @Column(columnDefinition = "TEXT")
    private String documentRemark;  // 비고/관리용 메모

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;
}
