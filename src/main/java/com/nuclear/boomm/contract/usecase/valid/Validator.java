package com.nuclear.boomm.contract.usecase.valid;

import com.nuclear.boomm.contract.domain.DraftContract;

public interface Validator {
    void validateForSubmit(DraftContract draft);
}
