package com.nuclear.boomm.product.dto.request.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RiskReportRequest(
        @NotBlank
        String competitorProductComparison,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal lossRatioForecast
) {
}
