package com.nuclear.boomm.caraccident.exception;

import com.nuclear.boomm.common.error.NotFoundException;

public class IntakeFileNotFoundException extends NotFoundException {
    public IntakeFileNotFoundException(String message) {
        super(message);
    }
}
