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
    private Long userId; //고객

    @Column(nullable = false)
    private Long productId; //계약상품아이디

    private Long underwriterId;//인수심사 담당자

    @Column(nullable = false)
    private Long contractManagerId; //계약담당자

    @Column(nullable = false)
    private boolean FSSadmission; //금감원담당자

    @Builder
    public UnderWritingReviewEntity(Long fileId, Long userId, Long productId, Long contractmanagerId) {
        this.fileId = fileId;
        this.userId = userId;
        this.productId = productId;
        this.contractManagerId = contractManagerId;
    }

    public void assignUnderwriter(Long underwriterId) {
        this.underwriterId = underwriterId;

    }

    public void FSSadmission(boolean FSSadmission) {
        this.FSSadmission = FSSadmission;
    }

}

