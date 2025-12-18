package com.nuclear.boomm.underwriting.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.underwriting.enums.InsurancePurpose;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

public class UserInformation extends BaseEntity {

    @Id
    @Column(nullable = false)
    private Long userId; //

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsurancePurpose insurancePurpose; //보험 가입 목적

    @Column(columnDefinition="TEXT")
    private String medicalHistory; //최근 치료 이력

    @Column(nullable = false)
    private boolean recentHospitalization; //최근 5년 내 입원/수술 여부



}
