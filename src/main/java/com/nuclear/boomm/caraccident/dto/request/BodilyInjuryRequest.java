package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.domain.BodilyInjuryEntity;
import com.nuclear.boomm.caraccident.enums.BodyInjuryType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BodilyInjuryRequest extends GeneralAssessmentRequest {

    @NotNull(message = "손해배상 이행확인서 파일은 필수입니다.")
    private Long implementFileId;

    private Long remarkFileId;

    @NotNull(message = "소득 입증자료 파일은 필수입니다.")
    private Long jobEvidenceFileId;

    @NotNull(message = "부상 유형은 필수입니다.")
    private BodyInjuryType bodyInjuryType;

    public BodilyInjuryEntity toEntity() {
        return BodilyInjuryEntity.builder()
                .intakeManagerId(super.getIntakeManagerId())
                .accidentIntakeId(super.getAccidentIntakeId())
                .insureClaimFileId(super.getInsureClaimFileId())
                .proofFileId(super.getProofFileId())
                .implementFileId(this.implementFileId)
                .remarkFileId(this.remarkFileId)
                .jobEvidenceFileId(this.jobEvidenceFileId)
                .bodyInjuryType(this.bodyInjuryType)
                .build();
    }
}