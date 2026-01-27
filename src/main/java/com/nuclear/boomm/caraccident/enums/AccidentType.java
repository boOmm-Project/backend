package com.nuclear.boomm.caraccident.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccidentType {
    CAR_TO_CAR("차대차"),
    SOLO_DRIVING("차량단독"),
    CAR_TO_PERSON("차대인"),
    OTHER("그 외");

    private final String description;

    public String getDescription(){
        return description;
    }
}
