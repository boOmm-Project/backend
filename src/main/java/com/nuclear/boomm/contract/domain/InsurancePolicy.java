package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.contract.enums.ContractStatus;
import com.nuclear.boomm.contract.enums.PaymentCycle;
import com.nuclear.boomm.contract.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicy extends BaseEntity {

    @Id
    private String policyNumber;

    @Column(nullable=false)
    private String userId;

    @Column(nullable=false)
    private Long productId;

    @Column(nullable=false)
    private LocalDate startDate;

    @Column(nullable=false)
    private LocalDate endDate;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentCycle paymentCycle;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable=false)
    private Double totalPremium;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;
}
