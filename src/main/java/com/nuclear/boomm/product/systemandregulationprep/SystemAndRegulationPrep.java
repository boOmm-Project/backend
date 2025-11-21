package com.nuclear.boomm.product.systemandregulationprep;

import com.nuclear.boomm.common.BaseEntity;
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
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SystemAndRegulationPrep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prepId;

    private String systemDevelopmentReq;
    private String policyAndDescription;
    private String underwritingGuidelines;
    private String auth;
    private String report;
}
