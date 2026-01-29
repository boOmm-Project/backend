package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.domain.SelfBodilyInjury;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SelfBodilyInjuryRequest extends GeneralAssessmentRequest {

    private Long remarkFileId; // 필수 아님

    public SelfBodilyInjury toEntity() {
        return SelfBodilyInjury.builder()
                .intakeManagerId(super.getIntakeManagerId())
                .accidentIntakeId(super.getAccidentIntakeId())
                .insureClaimFileId(super.getInsureClaimFileId())
                .proofFileId(super.getProofFileId())
                .remarkFileId(this.remarkFileId)
                .build();
    }
}