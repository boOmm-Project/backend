package com.nuclear.boomm.underwriting.dto.response;

public record FssSendResponse(
        Long caseId,
        boolean sent,
        String message
) {
}
