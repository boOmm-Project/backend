package com.nuclear.boomm.reexamination.domain;


import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "reexamination")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Reexamination extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long underwritingReexaminationId; //재심사

    @Column(nullable = false)
    private Long fileId;

    @Column(nullable = false)
    private Long customerId; //고객

    @Column(nullable = false)
    private String productId; //계약상품아이디

    @Column(nullable = false)
    private Long contractManagerId; //계약 담당자

    private Long reexaminationManagerId; // 재심사 담당자

    @Builder
    public Reexamination(Long fileId, Long customerId, String productId, Long contractManagerId) {
        this.fileId = fileId;
        this.customerId = customerId;
        this.productId = productId;
        this.contractManagerId = contractManagerId;
    }

    public void assignreexaminationManagerId(Long reexaminationManagerId) {
        this.reexaminationManagerId = reexaminationManagerId;
    }

}