package com.nuclear.boomm.caraccident.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PhoneType {
    CELL_PHONE("휴대전화"),
    HOME_PHONE("집");

    private final String description;

    public String getDescription()
    {
        return description;
    }
}
