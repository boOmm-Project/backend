package com.nuclear.boomm.caraccident.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record AccidentIntakeDTO(

        @NotNull(message = "사고 일시는 필수입니다.")
        @PastOrPresent(message = "사고 일시는 미래일 수 없습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        @Schema(example = "2024-12-19 21:00", description = "사고일자")
        LocalDateTime incidentDate,

        @NotNull(message = "차량번호는 필수입니다.")
        @Schema(example = "14허3325", description = "차량번호")
        String carNumber

) {
}
