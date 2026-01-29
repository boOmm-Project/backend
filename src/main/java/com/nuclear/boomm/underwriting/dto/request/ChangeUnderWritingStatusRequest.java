package com.nuclear.boomm.underwriting.dto.request;

import com.nuclear.boomm.underwriting.enums.RejectReason;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeUnderWritingStatusRequest(
        @NotNull UnderWritingStatus status,
        RejectReason rejectReason,
        String message
) {
}
