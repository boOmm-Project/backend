package com.nuclear.boomm.underwriting.dto.request;

import java.math.BigDecimal;

public record LossRatioAnalyzeRequest(
        Long underwritingReviedId,
        BigDecimal expectedPremium
) {
}
