package com.nuclear.boomm.caraccident.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;

import java.util.List;

public record AccidentIntakeDamageDescriptionDTO (
        @Valid List<BrokenObjectDTO> brokenList,
        @Valid List<InjuryPersonDTO> personList
) {
    @JsonIgnore
    public boolean isChecked(){
        boolean hasBroken = (brokenList != null && !brokenList.isEmpty());
        boolean hasPerson = (personList != null && !personList.isEmpty());

        return hasBroken || hasPerson;
    }
}
