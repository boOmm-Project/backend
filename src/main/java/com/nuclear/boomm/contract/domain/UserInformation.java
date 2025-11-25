package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.contract.enums.InsurancePurpose;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_information")
@Getter
@NoArgsConstructor
public class UserInformation extends BaseEntity {

    @Id
    @Column(nullable = false)
    private Long userId;   // 고객 ID FK

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsurancePurpose insurancePurpose;

    @Column(columnDefinition="text")
    private String medicalHistory;

    @Column(nullable = false)
    private boolean recentHospitalization;
}
