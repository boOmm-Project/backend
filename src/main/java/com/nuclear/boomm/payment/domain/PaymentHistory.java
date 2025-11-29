package com.nuclear.boomm.payment.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payment_history")
@NoArgsConstructor
public class PaymentHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentHistoryId;

    @Column(nullable = false)
    private String policyNumber;

    @Column(nullable = false)
    private Long paymentAttemptId;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private LocalDateTime paidDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal scheduledAmount;
}
