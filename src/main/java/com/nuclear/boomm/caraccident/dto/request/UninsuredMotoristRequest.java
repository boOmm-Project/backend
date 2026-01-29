package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.domain.UninsuredMotorist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UninsuredMotoristRequest extends GeneralAssessmentRequest {

    @NotBlank(message = "배상의무자 성명은 필수입니다.")
    private String liablePartyName;

    @NotBlank(message = "배상의무자 주소는 필수입니다.")
    private String liablePartyAddress;

    @NotBlank(message = "배상의무자 차량번호는 필수입니다.")
    private String libalePartyVehicleNumber;

    @NotNull(message = "상대방 대인2 가입 여부는 필수입니다.")
    private Boolean liablePartyBi2Joined;

    @NotBlank(message = "상대방 보험 내용은 필수입니다.")
    private String liablePartyInsuranceDetails;

    @NotNull(message = "기수령 손해배상금은 필수입니다.")
    private Long deductibleAmount;

    // Entity Builder에 이 필드가 빠져있다면 Entity 수정이 필요할 수 있습니다.
    // 일단 DTO에는 받아두도록 합니다.
    private Long victimRemarkFileId;

    public UninsuredMotorist toEntity() {
        return UninsuredMotorist.builder()
                .intakeManagerId(super.getIntakeManagerId())
                .accidentIntakeId(super.getAccidentIntakeId())
                .insureClaimFileId(super.getInsureClaimFileId())
                .proofFileId(super.getProofFileId())
                .liablePartyName(this.liablePartyName)
                .liablePartyAddress(this.liablePartyAddress)
                .libalePartyVehicleNumber(this.libalePartyVehicleNumber)
                .liablePartyBi2Joined(this.liablePartyBi2Joined)
                .liablePartyInsuranceDetails(this.liablePartyInsuranceDetails)
                .deductibleAmount(this.deductibleAmount)
                .build();
    }
}