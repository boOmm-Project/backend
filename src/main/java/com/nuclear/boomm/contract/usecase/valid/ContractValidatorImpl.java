package com.nuclear.boomm.contract.usecase.valid;

import com.nuclear.boomm.contract.domain.DraftContract;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ContractValidatorImpl implements Validator {

    @Override
    public void validateForSubmit(DraftContract draft) {
        // 상품 미선택
        if (draft.getProductId() == null) {
            throw new IllegalArgumentException("가입하실 보험 상품을 선택해주세요!");
        }

        // 가입 목적 미기입
        if (draft.getInsurancePurpose() == null) {
            throw new IllegalArgumentException("보험 가입 목적을 선택해주세요!");
        }

        // 치료 이력 미기입
        if (draft.getMedicalHistory() == null || draft.getMedicalHistory().isBlank()) {
            throw new IllegalArgumentException("과거 치료 이력을 상세히 적어주세요!");
        }

        // 보험료 미산출
        if (draft.getTotalPremium() == null || draft.getTotalPremium().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("보험료 계산이 완료되지 않았습니다!");
        }

        // 시작일 유효하지 않음
        if (draft.getStartDate() != null && draft.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("보험 시작일은 오늘 이후여야 합니다!");
        }
    }
}
