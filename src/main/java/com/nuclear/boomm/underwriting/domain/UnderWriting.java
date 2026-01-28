package com.nuclear.boomm.underwriting.domain;

import com.nuclear.boomm.common.BaseEntity;

import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "underwriting_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UnderWriting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long underwritingReviewId;  //인수심사

    @Column(nullable = false)
    private Long fileId;

    @Column(nullable = false)
    private Long userId; //고객

    @Column(nullable = false)
    private Long productId; //계약상품아이디

    private Long underwriterId;//인수심사 담당자

    @Column(nullable = false)
    private Long contractManagerId; //계약담당자

    @Column(nullable = false)
    private boolean fssadmission; //금감원담당자

    @Builder
    public UnderWriting(Long fileId, Long userId, Long productId, Long contractmanagerId) {
        this.fileId = fileId;
        this.userId = userId;
        this.productId = productId;
        this.contractManagerId = contractManagerId;
    }

    public void assignUnderwriter(Long underwriterId) {
        this.underwriterId = underwriterId;

    }

    public void fssadmission(boolean FSSadmission) {
        this.fssadmission = fssadmission;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnderWritingStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RejectReason rejectreason;



}

