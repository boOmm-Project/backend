package com.nuclear.boomm.caraccident.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "PropertyDamage")
public class PropertyDamage extends GeneralAccidentAssessmentEntity{
    @Column(nullable = false)
    private Long implementFileId; //손해배상의 이행사실을 증명하는 서류 id

    @Column()
    private Long remarkFileId; // 그 밖에 보험회사가 꼭 필요하여 요청하는 서류 id

    @Column(nullable = false)
    private Long evidenceFileId; // 피해자 측 손해 견적서 및 손해액 입증자료

    @Builder
    public PropertyDamage(Long intakeManagerId, Long accidentIntakeId, Long insureClaimFileId, Long proofFileId, Long implementFileId, Long remarkFileId, Long evidenceFileId) {
        super(intakeManagerId, accidentIntakeId, insureClaimFileId, proofFileId);
        this.implementFileId = implementFileId;
        this.remarkFileId = remarkFileId;
        this.evidenceFileId = evidenceFileId;
    }
}
