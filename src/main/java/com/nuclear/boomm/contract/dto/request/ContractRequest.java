package com.nuclear.boomm.contract.dto.request;

import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.underwriting.enums.InsurancePurpose;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractRequest(
        Long userId,
        Long productId,
        InsurancePurpose insurancePurpose,
        String medicalHistory,
        boolean recentHospitalization,
        LocalDate startDate,
        BigDecimal totalPremium,
        boolean isSubmitAction    // true: 심사 신청, false: 임시 저장
) {
    public DraftContract toEntity() {
        return DraftContract.builder()
                .userId(userId)
                .productId(productId)
                .insurancePurpose(insurancePurpose)
                .medicalHistory(medicalHistory)
                .recentHospitalization(recentHospitalization)
                .startDate(startDate)
                .totalPremium(totalPremium)
                .build();
    }
}
