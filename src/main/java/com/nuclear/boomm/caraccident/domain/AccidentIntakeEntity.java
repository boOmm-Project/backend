package com.nuclear.boomm.caraccident.domain;

import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDescriptionDTO;
import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.caraccident.enums.InsuranceClaimStatus;
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

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "car_accident_intake")
public class AccidentIntakeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String accidentDescription; // 사고 사항

    @Column(columnDefinition = "TEXT")
    private String damageDescription; // 피해 사항

    @Column(nullable = false)
    private LocalDateTime incidentDate; // 사고 일자

    @Column(nullable = false)
    private String policyNumber; // 증권번호

    @Column(nullable = false)
    private Long insuredPersonId; // 보험금 청구 접수자 유저 아이디

    @Column(nullable = false)
    private String insuranceClaimPersonName; // 보험금청구 접수자 이름

    @Enumerated(EnumType.STRING)
    private InsuranceClaimStatus intakeStatus; // 진행 상태

    @Column
    private Long intakeManagerId; // 접수 담당자 아이디

    @Column(columnDefinition = "TEXT")
    private String supplimentText;

    public AccidentIntakeEntity updateStatus(InsuranceClaimStatus status) {
        this.intakeStatus = status;
        return this;
    }

    public AccidentIntakeEntity assignIntakeManager(Long intakeManagerId) {
        this.intakeManagerId = intakeManagerId;
        return this;
    }

    public AccidentIntakeEntity updateSupplimentText(String supplimentText) {
        this.supplimentText = supplimentText;
        return this;
    }

    @Builder
    public AccidentIntakeEntity(LocalDateTime incidentDate, String policyNumber, Long insuredPersonId, String insuranceClaimPersonName, InsuranceClaimStatus intakeStatus) {
        this.incidentDate = incidentDate;
        this.policyNumber = policyNumber;
        this.insuredPersonId = insuredPersonId;
        this.insuranceClaimPersonName = insuranceClaimPersonName;
        this.intakeStatus = intakeStatus;
    }

    public void updateDescription(AccidentIntakeDescriptionDTO dto) {
        this.accidentDescription = dto.accidentDescription();
        this.damageDescription = dto.damageDescription();
    }


    public static AccidentIntakeEntity from(AccidentIntakeDTO dto, Long userId, String name) {
        return AccidentIntakeEntity.builder()
                .incidentDate(dto.incidentDate())
                .policyNumber(dto.policyNumber())
                .insuredPersonId(userId)
                .insuranceClaimPersonName(name)
                .intakeStatus(InsuranceClaimStatus.WRITING_ACCIDENT)
                .build();
    }

}
