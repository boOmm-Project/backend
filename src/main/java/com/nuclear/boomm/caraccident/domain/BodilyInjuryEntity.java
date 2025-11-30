package com.nuclear.boomm.caraccident.domain;

import com.nuclear.boomm.caraccident.enums.BodyInjuryType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "BodyInjury")
public class BodilyInjuryEntity extends GeneralAccidentAssessmentEntity {

    @Column(nullable = false)
    private Long implementFileId; //손해배상의 이행사실을 증명하는 서류 id

    @Column()
    private Long remarkFileId; // 그 밖에 보험회사가 꼭 필요하여 요청하는 서류 id

    @Column(nullable = false)
    private Long jobEvidenceFileId; // 직업 및 소득 입증자료

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BodyInjuryType bodyInjuryType;

    @Builder
    public BodilyInjuryEntity(Long intakeManagerId,
                              Long accidentIntakeId,
                              Long insureClaimFileId,
                              Long proofFileId,
                              Long implementFileId,
                              Long remarkFileId,
                              Long jobEvidenceFileId,
                              BodyInjuryType bodyInjuryType) {

        super(intakeManagerId, accidentIntakeId, insureClaimFileId, proofFileId);

        this.implementFileId = implementFileId;
        this.remarkFileId = remarkFileId;
        this.jobEvidenceFileId = jobEvidenceFileId;
        this.bodyInjuryType = bodyInjuryType;
    }
}
