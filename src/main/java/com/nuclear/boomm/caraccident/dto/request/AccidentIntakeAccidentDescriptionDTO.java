package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.enums.AccidentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccidentIntakeAccidentDescriptionDTO(

        @NotNull(message = "사고 유형이 없을수는 없습니다.")
        @Schema(description = "사고 유형에 대해서 작성해주세요", allowableValues = {"CAR_TO_CAR","SOLO_DRIVING","CAR_TO_PERSON","OTHER"})
        AccidentType accidentType,

        @NotNull(message = "우편번호가 없을 수는 없습니다.")
        String zipCode,

        @NotBlank(message = "사고 장소가 없을 수는 없습니다.")
        String accidentPlace,

        @NotNull(message = "사고 장소의 세부 주소를 입력해주세요")
        String detailPlace,

        Boolean isReport,

        @Schema(description = "햇빛경찰서")
        String policeStation,

        @NotBlank(message = "사고 내용이 없을 수는 없습니다.")
        String accidentDescription

) {
}
