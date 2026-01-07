package com.nuclear.boomm.product.dto.response.product;

import com.nuclear.boomm.product.domain.RiskReport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RiskReportResponse(
        @NotNull
        Long reportId,

        @NotNull
        Long productId,

        @NotNull
        BigDecimal lossRatioForecast,

        @NotBlank
        String competitorProductComparison
) {
    public static RiskReportResponse from(RiskReport report) {
        return new RiskReportResponse(
                report.getReportId(),
                report.getProduct().getProductId(),
                report.getLossRatioForecast(),
                report.getCompetitorProductComparison()
        );
    }
}
