package com.nuclear.boomm.caraccident.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "SelfVehicleDamage")
public class SelfVehicleDamage extends GeneralAccidentAssessmentEntity {

    @Column(nullable = false)
    private String competentPoliceStation;     // 사고가 발생한 때와 장소 및 사고 사실이 신고된 관할 경찰관서

    @Column
    private Long vehicleCancellationProofFileId;     //도난 및 전손사고의 경우 폐차증명서 또는 말소 사실증명서 파일 아이디

    @Column
    private Long remarkFileId; // 그 밖에 보험회사가 꼭 필요하여 요청하는 서류 id

    public SelfVehicleDamage(Long intakeManagerId, Long accidentIntakeId, Long insureClaimFileId, Long proofFileId, String competentPoliceStation, Long vehicleCancellationProofFileId, Long remarkFileId) {
        super(intakeManagerId, accidentIntakeId, insureClaimFileId, proofFileId);
        this.competentPoliceStation = competentPoliceStation;
        this.vehicleCancellationProofFileId = vehicleCancellationProofFileId;
        this.remarkFileId = remarkFileId;
    }
}
