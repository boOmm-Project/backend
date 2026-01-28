package com.nuclear.boomm.caraccident.dto.response;

import com.nuclear.boomm.caraccident.domain.AccidentFileEntity;

public record AccidentIntakeFileDTO(
        Long fileId,
        String url
)
{
    public static AccidentIntakeFileDTO fromEntity(AccidentFileEntity entity) {
        return new AccidentIntakeFileDTO(
                entity.getId(),
                entity.getFileContent()
        );
    }
}
