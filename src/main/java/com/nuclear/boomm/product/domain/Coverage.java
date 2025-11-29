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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "coverage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coverage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverageId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoverageCategory category;

    @Column(nullable = false, unique = true)
    private Long productId; // product 테이블의 productId 참조

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double minCoverageLimit;

    @Column(nullable = false)
    private double maxCoverageLimit;

    @Column(nullable = false)
    private boolean isMandatory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String damageCalStandard;

    @Builder
    public Coverage(CoverageCategory category, Long productId, String description, double minCoverageLimit, double maxCoverageLimit, boolean isMandatory, String damageCalStandard) {
        this.category = category;
        this.productId = productId;
        this.description = description;
        this.minCoverageLimit = minCoverageLimit;
        this.maxCoverageLimit = maxCoverageLimit;
        this.isMandatory = isMandatory;
        this.damageCalStandard = damageCalStandard;
    }


}
