package com.nuclear.boomm.contract.dto.request;

import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.underwriting.enums.InsurancePurpose;
import com.nuclear.boomm.vehicle.domain.VehicleInfo;
import com.nuclear.boomm.vehicle.enums.FuelType;
import com.nuclear.boomm.vehicle.enums.UsePurpose;
import com.nuclear.boomm.vehicle.enums.VehicleType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ContractRequest(
        Long userId,
        Long productId,
        InsurancePurpose insurancePurpose,
        String medicalHistory,
        boolean recentHospitalization,
        LocalDate startDate,
        BigDecimal totalPremium,
        String vehicleNumber,
        String vin,
        VehicleType vehicleType,
        String manufacturer,
        String modelName,
        Integer modelYear,
        FuelType fuelType,
        UsePurpose usePurpose,
        Integer displacement,
        Integer seatCount,
        LocalDate firstRegistrationDate,
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
                .vehicleInfo(VehicleInfo.builder()
                                .vehicleNumber(vehicleNumber)
                                .vin(vin)
                                .vehicleType(vehicleType)
                                .manufacturer(manufacturer)
                                .modelName(modelName)
                                .modelYear(modelYear)
                                .fuelType(fuelType)
                                .usePurpose(usePurpose)
                                .displacement(displacement)
                                .seatCount(seatCount)
                                .firstRegistrationDate(firstRegistrationDate)
                        .build()
                )
                .build();
    }
}
