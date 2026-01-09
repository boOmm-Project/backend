package com.nuclear.boomm.caraccident.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AccidentIntakeDescriptionDTO(
        @NotBlank(message = "사고 내용이 없을 수는 없습니다.")
        String accidentDescription,
        @NotBlank(message = "피해 내용이 없을 수는 없습니다.")
        String damageDescription
) {
}
