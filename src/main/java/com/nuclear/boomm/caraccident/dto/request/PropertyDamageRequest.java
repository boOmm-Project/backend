package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.domain.PropertyDamage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertyDamageRequest extends GeneralAssessmentRequest {

    @NotNull(message = "손해배상 이행확인서 파일은 필수입니다.")
    private Long implementFileId;

    private Long remarkFileId;

    @NotNull(message = "손해 견적서 파일은 필수입니다.")
    private Long evidenceFileId;

    public PropertyDamage toEntity() {
        return PropertyDamage.builder()
                .intakeManagerId(super.getIntakeManagerId())
                .accidentIntakeId(super.getAccidentIntakeId())
                .insureClaimFileId(super.getInsureClaimFileId())
                .proofFileId(super.getProofFileId())
                .implementFileId(this.implementFileId)
                .remarkFileId(this.remarkFileId)
                .evidenceFileId(this.evidenceFileId)
                .build();
    }
}
