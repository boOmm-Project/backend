package com.nuclear.boomm.reexamination.domain;


import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.underwriting.enums.RejectReason;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "reexamination")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Reexamination extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reexaminationId; //재심사

    @Column(nullable = false)
    private Long fileId;

    @Column(nullable = false)
    private Long customerId; //고객

    @Column(nullable = false)
    private Long productId; //계약상품아이디

    @Column(nullable = false)
    private Long contractManagerId; //계약 담당자

    private Long reexaminationManagerId; // 재심사 담당자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnderWritingStatus status = UnderWritingStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private RejectReason rejectReason;

    @Column(columnDefinition = "TEXT")
    private String resultMessage;

    private LocalDateTime reviewedAt;

    @Builder
    public Reexamination(Long fileId, Long customerId, Long productId, Long contractManagerId) {
        this.fileId = fileId;
        this.customerId = customerId;
        this.productId = productId;
        this.contractManagerId = contractManagerId;
        this.status = UnderWritingStatus.PENDING;
    }

    public void assignReexaminationManager(Long managerId) {
        this.reexaminationManagerId = managerId;
        this.status = UnderWritingStatus.IN_PROGRESS;
    }

    public void complete(String message) {
        this.status = UnderWritingStatus.COMPLETED;
        this.resultMessage = message;
        this.reviewedAt = LocalDateTime.now();
        this.rejectReason = null;
    }

    public void reject(RejectReason reason, String message) {
        this.status = UnderWritingStatus.REJECTED;
        this.rejectReason = reason;
        this.resultMessage = message;
        this.reviewedAt = LocalDateTime.now();
    }
}