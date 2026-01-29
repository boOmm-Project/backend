package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.domain.SelfVehicleDamage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SelfVehicleDamageRequest extends GeneralAssessmentRequest {

    @NotBlank(message = "관할 경찰서 정보는 필수입니다.")
    private String competentPoliceStation;

    private Long vehicleCancellationProofFileId;

    private Long remarkFileId;

    public SelfVehicleDamage toEntity() {
       return SelfVehicleDamage.builder()
               .intakeManagerId(super.getIntakeManagerId())
               .accidentIntakeId(super.getAccidentIntakeId())
               .insureClaimFileId(super.getInsureClaimFileId())
               .proofFileId(super.getProofFileId())
               .competentPoliceStation(competentPoliceStation)
               .vehicleCancellationProofFileId(vehicleCancellationProofFileId)
               .remarkFileId(remarkFileId)
               .build();
    }
}