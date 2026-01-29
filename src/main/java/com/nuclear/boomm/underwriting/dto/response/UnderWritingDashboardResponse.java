package com.nuclear.boomm.underwriting.dto.response;

public record UnderWritingDashboardResponse(
        long pending,
        long inProgress,
        long completed,
        long rejected
) {
}
