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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemAndRegulationPrep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prepId;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String systemDevelopmentReq;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String policyAndDescription;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isConfirmed;    // 금융감독원 인가

    @Builder
    public SystemAndRegulationPrep(String policyAndDescription, boolean isConfirmed) {
        this.policyAndDescription = policyAndDescription;
        this.isConfirmed = isConfirmed;
    }

    @Builder(builderMethodName = "withSystemDevelopmentReq")
    public SystemAndRegulationPrep(String systemDevelopmentReq, String policyAndDescription, boolean isConfirmed) {
        this.systemDevelopmentReq = systemDevelopmentReq;
        this.policyAndDescription = policyAndDescription;
        this.isConfirmed = isConfirmed;
    }
}
