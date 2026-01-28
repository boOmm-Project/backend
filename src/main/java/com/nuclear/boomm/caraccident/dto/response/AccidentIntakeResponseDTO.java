package com.nuclear.boomm.caraccident.dto.response;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;

import java.time.LocalDateTime;

public record AccidentIntakeResponseDTO(
        Long intakeId,
        String insuranceClaimPersonName,
        LocalDateTime incidentDate,
        String intakeStatus,
        String supplimentText


) {
    public static AccidentIntakeResponseDTO fromEntity(AccidentIntakeEntity entity) {
        return new AccidentIntakeResponseDTO(
                entity.getId(),
                entity.getInsuranceClaimPersonName(),
                entity.getIncidentDate(),
                entity.getIntakeStatus().getDescription(),
                entity.getSupplimentText()
        );
    }
}
