package com.nuclear.boomm.underwriting.dto.response;

import java.math.BigDecimal;

public record LossRatioAnlyzeResponse(
        String message,
        BigDecimal expectedPremium,
        BigDecimal lossRatio
) {
}
