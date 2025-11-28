package com.nuclear.boomm.underwriting.domain;

import com.nuclear.boomm.common.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "underwriting_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UnderWritingReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long underwritingReviewId;  //인수심사

    @Column(nullable = false)
    private Long fileId;

    @Column(nullable = false)
    private Long customerId; //고객

    @Column(nullable = false)
    private Long productId; //계약상품아이디

    private Long underwriterId;//인수심사 담당자

    @Column(nullable = false)
    private Long contractmanagerId; //계약담당자

    @Column(nullable = false)
    private boolean Fssadmission; //금감원담당자

    @Builder
    public UnderWritingReviewEntity(Long fileId, Long customerId, Long productId, Long contractmanagerId) {
        this.fileId = fileId;
        this.customerId = customerId;
        this.productId = productId;
        this.contractmanagerId = contractmanagerId;
    }

    public void assignUnderwriter(Long underwriterId) {
        this.underwriterId = underwriterId;

    }

    public void Fssadmission(boolean Fssadmission) {
        this.Fssadmission = Fssadmission;
    }

}

