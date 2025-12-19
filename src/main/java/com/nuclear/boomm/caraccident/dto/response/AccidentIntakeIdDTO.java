package com.nuclear.boomm.caraccident.dto.response;

public record AccidentIntakeIdDTO(
        Long intakeId
) {

    public static AccidentIntakeIdDTO from(Long id){
        return new AccidentIntakeIdDTO(id);
    }
}
