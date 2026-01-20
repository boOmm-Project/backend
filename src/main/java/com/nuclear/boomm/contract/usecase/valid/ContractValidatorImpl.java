package com.nuclear.boomm.contract.usecase.valid;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.error.CustomException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ContractValidatorImpl implements Validator {

    @Override
    public void validateForSubmit(DraftContract draft) {

        // 요청 유효성 검증
        if(draft == null) {
            throw new CustomException(ErrorCode.CONTRACT_REQUEST_IS_INVALID);
        }

        // 상품 미선택
        if (draft.getProductId() == null) {
            throw new CustomException(ErrorCode.CONTRACT_PRODUCT_NOT_SELECTED);
        }

        // 가입 목적 미기입
        if (draft.getInsurancePurpose() == null) {
            throw new CustomException(ErrorCode.CONTRACT_PURPOSE_MISSING);
        }

        // 치료 이력 미기입
        if (draft.getMedicalHistory() == null || draft.getMedicalHistory().isBlank()) {
            throw new CustomException(ErrorCode.CONTRACT_MEDICAL_HISTORY_MISSING);
        }

        // 보험료 미산출
        if (draft.getTotalPremium() == null || draft.getTotalPremium().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException(ErrorCode.CONTRACT_PREMIUM_NOT_CALCULATED);
        }

        // 시작일 유효하지 않음
        if (draft.getStartDate() != null && draft.getStartDate().isBefore(LocalDate.now())) {
            throw new CustomException(ErrorCode.CONTRACT_INVALID_START_DATE);
        }
    }
}
