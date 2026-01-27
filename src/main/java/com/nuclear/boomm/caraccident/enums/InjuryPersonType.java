package com.nuclear.boomm.caraccident.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InjuryPersonType {
    SELF_BODY("본인"),
    FAMILY("가족"),
    COLLEAGUE("동료"),
    OTHER_PERSON("기타");

    private final String description;

    public String getDescription(){
        return description;
    }
}
