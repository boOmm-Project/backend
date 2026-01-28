package com.nuclear.boomm.caraccident.dto.response;

import com.nuclear.boomm.caraccident.domain.BrokenObjectEntity;

public record BrokenObjectResponseDTO(
    String brokenObjectType,
    String name,
    String phoneType,
    String phoneNumber,
    String repairPlace
) {
    public static BrokenObjectResponseDTO fromEntity(BrokenObjectEntity entity) {
        return new BrokenObjectResponseDTO(
                entity.getBrokenObejctType().getDescription(),
                entity.getName(),
                entity.getPhoneType().getDescription(),
                entity.getPhoneNumber(),
                entity.getRepairPlace()
        );
    }
}
