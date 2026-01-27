package com.nuclear.boomm.caraccident.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BrokenObejctType {
    SELF_VEHICLE("자차"),
    OPPO_VEHICLE("상대차"),
    OTHER_OBJECT("기타피해물");

    private final String description;

    public String getDescription(){
        return description;
    }
}
