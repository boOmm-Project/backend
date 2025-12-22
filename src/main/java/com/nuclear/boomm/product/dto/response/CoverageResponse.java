package com.nuclear.boomm.product.dto.response;

import com.nuclear.boomm.product.domain.Coverage;

import java.util.List;

public record CoverageResponse(
    String title,
    String description,
    double minCoverageLimit,
    double maxCoverageLimit,
    boolean isMandatory
) {
    public static CoverageResponse from(Coverage coverage) {
        return new CoverageResponse(
                coverage.getTitle(),
                coverage.getDescription(),
                coverage.getMinCoverageLimit(),
                coverage.getMaxCoverageLimit(),
                coverage.isMandatory()
        );
    }

    public static List<CoverageResponse> from(List<Coverage> coverages) {
        return coverages.stream()
                .map(CoverageResponse::from)
                .toList();
    }
}
