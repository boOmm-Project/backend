package com.nuclear.boomm.product.dto.response.product;

import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.domain.RiskReport;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record RiskReportDetailResponse(
        @NotNull
        Long reportId,

        @NotNull
        BigDecimal lossRatioForecast,

        @NotNull
        String competitorProductComparison,

        List<ProductFileResponse> fileResponseList
) {
    public static RiskReportDetailResponse from(RiskReport report, List<ProductFile> fileList) {
        return new RiskReportDetailResponse(
                report.getReportId(),
                report.getLossRatioForecast(),
                report.getCompetitorProductComparison(),
                ProductFileResponse.from(fileList)
        );
    }
}
