package com.nuclear.boomm.underwriting.dto.request;

import jakarta.validation.constraints.NotNull;

public record AssignUnderWriterRequest(
        @NotNull Long underwriterId
) {
}
