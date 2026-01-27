package com.nuclear.boomm.caraccident.dto.request;

import com.nuclear.boomm.caraccident.enums.BrokenObejctType;
import com.nuclear.boomm.caraccident.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;

public record BrokenObjectDTO(
    BrokenObejctType brokenObejctType,
    @NotBlank(message = "이름이 없을 수는 없습니다.")
    String name,
    PhoneType phoneType,
    @NotBlank(message = "전화번호가 없을수는 없습니다.")
    String phoneNumber,
    String repairPlace
) {
}
