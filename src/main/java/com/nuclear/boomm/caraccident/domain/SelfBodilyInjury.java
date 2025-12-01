package com.nuclear.boomm.caraccident.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "SelfBodilyInjury")
public class SelfBodilyInjury extends GeneralAccidentAssessmentEntity {
    @Column()
    private Long remarkFileId; // 그 밖에 보험회사가 꼭 필요하여 요청하는 서류 id

    @Builder
    public SelfBodilyInjury(Long intakeManagerId, Long accidentIntakeId, Long insureClaimFileId, Long proofFileId, Long remarkFileId) {

        super(intakeManagerId, accidentIntakeId, insureClaimFileId, proofFileId);

        this.remarkFileId = remarkFileId;
    }
}
