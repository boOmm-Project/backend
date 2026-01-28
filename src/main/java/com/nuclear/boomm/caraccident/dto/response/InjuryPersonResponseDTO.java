package com.nuclear.boomm.caraccident.dto.response;

import com.nuclear.boomm.caraccident.domain.InjuryPersonEntity;

public record InjuryPersonResponseDTO(
        String injuryPersonType,
        String name,
        String phoneType,
        String phoneNumber,
        String hospitalName
) {
    public static InjuryPersonResponseDTO fromEntity(InjuryPersonEntity entity){
        return new InjuryPersonResponseDTO(
                entity.getInjuryPersonType().getDescription(),
                entity.getName(),
                entity.getPhoneType().getDescription(),
                entity.getPhoneNumber(),
                entity.getHospitalName()
        );
    }
}
