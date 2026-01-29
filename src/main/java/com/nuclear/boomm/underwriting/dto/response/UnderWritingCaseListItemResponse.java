package com.nuclear.boomm.underwriting.dto.response;

import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;

import java.time.LocalDateTime;

public record UnderWritingCaseListItemResponse(
        Long id,
        Long customerId,
        Long productId,
        UnderWritingStatus status,
        Long underwriterId,
        LocalDateTime reviewedAt

) {
}
