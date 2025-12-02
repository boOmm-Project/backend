package com.nuclear.boomm.payment.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.payment.enums.FailureMessage;
import com.nuclear.boomm.payment.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_attempt")
@Getter
@NoArgsConstructor
public class PaymentAttempt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String policyNumber;        // fk

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal paymentAmount;

    @Column(nullable = false)
    private Long paymentId;         // fk

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;        // 결제 상태

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FailureMessage failureMessage;
}
