package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.contract.enums.ContractStatus;
import com.nuclear.boomm.contract.enums.PaymentCycle;
import com.nuclear.boomm.contract.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicy extends BaseEntity {

    @Id
    private String policyNumber;    // fk, pk

    @Column(nullable=false)
    private String userId;      // fk

    @Column(nullable=false)
    private Long productId;     // fk

    @Column(nullable=false)
    private LocalDate startDate;    // 보험 시작일

    @Column(nullable=false)
    private LocalDate endDate;      // 보험 만기일

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentCycle paymentCycle;  // 납입 주기

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable=false, precision = 10, scale = 2)
    private BigDecimal totalPremium;    // 총 보험료

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;  // 계약 상태
}
