package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.enums.InjuryPersonType;
import com.nuclear.boomm.caraccident.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InjuryPersonDTO(
        InjuryPersonType injuryPersonType,
        @NotBlank(message = "이름이 없을 수는 없습니다.")
        String name,
        PhoneType phoneType,
        @NotBlank(message = "전화번호가 없을수는 없습니다.")
        String phoneNumber,
        String hospitalName
) {
}
