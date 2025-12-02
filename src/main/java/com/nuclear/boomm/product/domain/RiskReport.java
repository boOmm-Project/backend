package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId; // product 테이블 pk 참조

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal lossRatioPredict;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String competitorProductComparison;

    @Builder
    public RiskReport(Long productId, BigDecimal lossRatioPredict, String competitorProductComparison) {
        this.productId = productId;
        this.lossRatioPredict = lossRatioPredict;
        this.competitorProductComparison = competitorProductComparison;
    }
}
