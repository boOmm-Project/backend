package com.nuclear.boomm.underwriting.dto.response;

import jakarta.validation.constraints.NotNull;

public record AssignUnderWriterRequest(
        @NotNull Long underwriterId
) {
}
