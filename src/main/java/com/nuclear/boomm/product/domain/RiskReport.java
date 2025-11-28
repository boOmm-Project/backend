package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "risk_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private Long productId; // product 테이블 pk 참조

    @Column(nullable = false, unique = true)
    private Long productFileId; // product_file 테이블 pk 참조

    @Column(nullable = false)
    private Double lossRatioForecast;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String competitorProductComparison;

    @Builder
    public RiskReport(Long productId, Long productFileId, Double lossRatioForecast, String competitorProductComparison) {
        this.productId = productId;
        this.productFileId = productFileId;
        this.lossRatioForecast = lossRatioForecast;
        this.competitorProductComparison = competitorProductComparison;
    }
}
