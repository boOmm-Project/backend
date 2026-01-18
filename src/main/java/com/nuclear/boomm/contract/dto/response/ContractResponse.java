package com.nuclear.boomm.contract.dto.response;

import com.nuclear.boomm.contract.enums.ContractActionType;

public record ContractResponse(
        Long id,
        String message,
        ContractActionType actionType
) {
}
