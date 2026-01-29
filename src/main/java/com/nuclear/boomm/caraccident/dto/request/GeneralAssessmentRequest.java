package com.nuclear.boomm.caraccident.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class GeneralAssessmentRequest {

    @NotNull(message = "접수 담당자 ID는 필수입니다.")
    private Long intakeManagerId;

    @NotNull(message = "사고 접수 ID는 필수입니다.")
    private Long accidentIntakeId;

    @NotNull(message = "보험금 청구서 파일 ID는 필수입니다.")
    private Long insureClaimFileId;

    private Long proofFileId; // 손해액 증명 서류 (필수가 아닐 수 있어 NotNull 제외)

}