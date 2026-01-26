package com.nuclear.boomm.contract.enums;

import lombok.Getter;

@Getter
public enum ContractActionType {
    SAVED("임시 저장 완료"),
    SUBMITTED("심사 신청 완료");

    private final String description;

    ContractActionType(String description) {
        this.description = description;
    }
}
