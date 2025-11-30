package com.nuclear.boomm.caraccident.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "accidentType")
public abstract class GeneralAccidentAssessmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long assessmentManagerId; // 심사 담당 매니저 ID

    @Column
    private Long intakeManagerId; // 접수 담당자 ID

    @Column
    private Long accidentIntakeId; // 사고 접수 ID

    @Column
    private Long insureClaimFileId; // 보험금 청구서 파일

    @Column
    private Long proofFileId; // 손해액을 증명하는 서류

    public GeneralAccidentAssessmentEntity(Long intakeManagerId, Long accidentIntakeId, Long insureClaimFileId, Long proofFileId) {
        this.intakeManagerId = intakeManagerId;
        this.accidentIntakeId = accidentIntakeId;
        this.insureClaimFileId = insureClaimFileId;
        this.proofFileId = proofFileId;
    }

    // 심사 담당자 할당
    public void assignManagerId(Long assessmentManagerId) {
        this.assessmentManagerId = assessmentManagerId;
    }

}
