package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.dto.request.product.CoverageRequest;
import com.nuclear.boomm.product.enums.CoverageCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@Table(name = "coverage", uniqueConstraints = {
        @UniqueConstraint(
                name = "PRODUCT_ID_TITLE_UNIQUE",
                columnNames = {"product_id", "title"}
        )
})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Coverage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverageId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoverageCategory category = CoverageCategory.MANDATORY_BASIC_COVERAGE;

    @Column(nullable = false)
    private Long productId; // product 테이블의 productId 참조

    @Builder.Default
    @Column(nullable = false)
    private String title = "임시 담보명";

    @Builder.Default
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description = "세부 설명";

    @Builder.Default
    @Column(nullable = false)
    private double minCoverageLimit = 0.0;

    @Builder.Default
    @Column(nullable = false)
    private double maxCoverageLimit = 0.0;

    @Builder.Default
    @Column(nullable = false)
    private boolean isMandatory = false;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "TEXT")
    private String damageCalStandard = "피해 산정 기준";

    public static Coverage create(CoverageRequest coverageRequest) {
        return Coverage.builder()
                .category(coverageRequest.category())
                .productId(coverageRequest.productId())
                .title(coverageRequest.title())
                .description(coverageRequest.description())
                .minCoverageLimit(coverageRequest.minCoverageLimit())
                .maxCoverageLimit(coverageRequest.maxCoverageLimit())
                .isMandatory(coverageRequest.isMandatory())
                .damageCalStandard(coverageRequest.damageCalStandard())
                .build();
    }

    public static List<Coverage> create(List<CoverageRequest> coverageRequests) {
        return coverageRequests.stream()
                .map(Coverage::create)
                .toList();
    }

    public void update(CoverageRequest request) {
        this.title = request.title();
        this.description = request.description();
        this.minCoverageLimit = request.minCoverageLimit();
        this.maxCoverageLimit = request.maxCoverageLimit();
        this.isMandatory = request.isMandatory();
        this.damageCalStandard = request.damageCalStandard();
    }
}
