package com.nuclear.boomm.contract.usecase.valid;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.error.CustomException;
import com.nuclear.boomm.vehicle.domain.VehicleInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ContractValidatorImpl implements Validator {

    @Override
    public void validateCarInfoForSubmit(DraftContract draft) {
        VehicleInfo vehicle = draft.getVehicleInfo();

        // 차량 정보 기입 유효성 검증
        if(vehicle == null){
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 차량 번호 유효성 검증
        if(vehicle.getVehicleNumber() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 차대 번호 유효성 검증
        if(vehicle.getVin() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 차종 유효성 검증
        if(vehicle.getVehicleType() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 제조사 유효성 검증
        if(vehicle.getManufacturer() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 모델명 유효성 검증
        if(vehicle.getModelName() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 연식 유효성 검증
        if(vehicle.getModelYear() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 연료 타입 유효성 검증
        if(vehicle.getFuelType() == null) {
            throw new CustomException(ErrorCode.REQUIRED_OPTION_NOT_SELECTED);
        }

        // 차량 구매 목적 유효성 검증
        if(vehicle.getUsePurpose() == null) {
            throw new CustomException(ErrorCode.REQUIRED_OPTION_NOT_SELECTED);
        }

        // 배기량 유효성 검증
        if(vehicle.getDisplacement() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 승차 정원 유효성 검증
        if(vehicle.getSeatCount() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

    }

    @Override
    public void validateForSubmit(DraftContract draft) {

        // 요청 유효성 검증
        if(draft == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 상품 미선택
        if (draft.getProductId() == null) {
            throw new CustomException(ErrorCode.REQUIRED_OPTION_NOT_SELECTED);
        }

        // 가입 목적 미기입
        if (draft.getInsurancePurpose() == null) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 치료 이력 미기입
        if (draft.getMedicalHistory() == null || draft.getMedicalHistory().isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 보험료 미산출
        if (draft.getTotalPremium() == null || draft.getTotalPremium().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }

        // 시작일 유효하지 않음
        if (draft.getStartDate() != null && draft.getStartDate().isBefore(LocalDate.now())) {
            throw new CustomException(ErrorCode.REQUIRED_INPUT_MISSING);
        }
    }
}
