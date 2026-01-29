package com.nuclear.boomm.underwriting.dto.response;

import com.nuclear.boomm.underwriting.enums.RejectReason;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;

import java.time.LocalDateTime;

public record UnderWritingCaseResponse(
        Long id,
        Long fileId,
        Long customerId,
        Long productId,
        Long underwriterId,
        Long contractManagerId,
        boolean fssAdmission,
        UnderWritingStatus status,
        RejectReason rejectReason,
        String resultMessage,
        LocalDateTime reviewedAt

) {
}
