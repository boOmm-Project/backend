package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.enums.CoverageCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Coverage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoverageCategory category;

    @Column(nullable = false, unique = true)
    private Long productId; // product 테이블의 id 참조

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal minCoverageLimit;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal maxCoverageLimit;

    @Column(nullable = false)
    private boolean isMandatory = false;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String damageCalculationStandard;

    @Builder(builderMethodName = "withMandatoryBuilder")
    public Coverage(CoverageCategory category, Long productId, String description, BigDecimal minCoverageLimit, BigDecimal maxCoverageLimit, boolean isMandatory, String damageCalculationStandard) {
        this.category = category;
        this.productId = productId;
        this.description = description;
        this.minCoverageLimit = minCoverageLimit;
        this.maxCoverageLimit = maxCoverageLimit;
        this.isMandatory = isMandatory;
        this.damageCalculationStandard = damageCalculationStandard;
    }

    @Builder
    public Coverage(CoverageCategory category, Long productId, String description, BigDecimal minCoverageLimit, BigDecimal maxCoverageLimit, String damageCalculationStandard) {
        this.category = category;
        this.productId = productId;
        this.description = description;
        this.minCoverageLimit = minCoverageLimit;
        this.maxCoverageLimit = maxCoverageLimit;
        this.damageCalculationStandard = damageCalculationStandard;
    }
}
