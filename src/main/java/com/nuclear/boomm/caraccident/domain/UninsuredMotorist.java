package com.nuclear.boomm.caraccident.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "UninsuredMotorist")
public class UninsuredMotorist extends GeneralAccidentAssessmentEntity {

    @Column(nullable = false)
    private String liablePartyName; // 배상의무자 성명

    @Column(nullable = false)
    private String liablePartyAddress; // 배상의무자 주소

    @Column(nullable = false)
    private String libalePartyVehicleNumber; // 배상의무자 차량번호

    @Column(nullable = false)
    private Boolean liablePartyBi2Joined; // 상대방 대인2 가입 여부

    @Column(nullable = false)
    private String liablePartyInsuranceDetails; // 상대방 보험 내용

    @Column(nullable = false)
    private Long deductibleAmount; // 이미 지급받은 손해 배상금액

    @Column
    private Long victimRemarkFileId; // 그 밖에 보험회사가 꼭 필요하여 요청하는 서류 id

    @Builder
    public UninsuredMotorist(Long intakeManagerId,
                             Long accidentIntakeId,
                             Long insureClaimFileId,
                             Long proofFileId,
                             String liablePartyName,
                             String liablePartyAddress,
                             String libalePartyVehicleNumber,
                             Boolean liablePartyBi2Joined,
                             String liablePartyInsuranceDetails,
                             Long deductibleAmount) {

        super(intakeManagerId, accidentIntakeId, insureClaimFileId, proofFileId);

        this.liablePartyName = liablePartyName;
        this.liablePartyAddress = liablePartyAddress;
        this.libalePartyVehicleNumber = libalePartyVehicleNumber;
        this.liablePartyBi2Joined = liablePartyBi2Joined;
        this.liablePartyInsuranceDetails = liablePartyInsuranceDetails;
        this.deductibleAmount = deductibleAmount;
    }
}
