package com.nuclear.boomm.underwriting.dto.response;

public record FssStatusResponse(
        Long casId,
        boolean fssAdmission,
        String StatusMessage
) {
}
