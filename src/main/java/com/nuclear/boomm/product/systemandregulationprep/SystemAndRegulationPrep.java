package com.nuclear.boomm.product.systemandregulationprep;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "system_and_regulation_prep")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemAndRegulationPrep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prepId;

    @Column(nullable = true)
    private String systemDevelopmentReq;

    @Column(nullable = false)
    private String policyAndDescription;

    @Column(nullable = true)
    private String underwritingGuidelines;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isConfirmed;    // 금융감독원 인가

    @Column(nullable = false)
    private String report;
}
