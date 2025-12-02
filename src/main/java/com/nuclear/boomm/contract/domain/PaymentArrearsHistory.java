package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_arrears_history")
@Getter
public class PaymentArrearsHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String policyNumber;  // FK

    @Column(nullable = false)
    private Long userId;    // fk

    @Column(nullable = false)
    private LocalDateTime missedAt;     // 미납 일시

    @Column
    private Integer missAmount;     // 미납 금액

    @Column(nullable = false)
    private Integer accumulatedMissCount;   // 미납 횟수

}

