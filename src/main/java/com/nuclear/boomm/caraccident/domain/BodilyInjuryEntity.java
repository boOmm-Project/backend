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

    // [수정 1] Java 변수명은 camelCase, DB 컬럼명은 name 속성으로 지정
    @Column(nullable = false)
    private Long implementFileId; //손해배상의 이행사실을 증명하는 서류 id

    @Column()
    private Long remarkFileId; // 그 밖에 보험회사가 꼭 필요하여 요청하는 서류 id

    @Column(nullable = false)
    private Long evidenceFileId; // 피해자 측 손해 견적서 및 손해액 입증자료

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
                              Long evidenceFileId,
                              BodyInjuryType bodyInjuryType) {

        super(intakeManagerId, accidentIntakeId, insureClaimFileId, proofFileId);

        this.implementFileId = implementFileId;
        this.remarkFileId = remarkFileId;
        this.evidenceFileId = evidenceFileId;
        this.bodyInjuryType = bodyInjuryType;
    }
}
