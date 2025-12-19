package com.nuclear.boomm.product.dto.request;

import com.nuclear.boomm.product.domain.Coverage;

public record CoverageRequest(
    String title,
    String description,
    double minCoverageLimit,
    double maxCoverageLimit,
    boolean isMandatory
) {
    public static CoverageRequest from(Coverage coverage) {
        return new CoverageRequest(
                coverage.getTitle(),
                coverage.getDescription(),
                coverage.getMinCoverageLimit(),
                coverage.getMaxCoverageLimit(),
                coverage.isMandatory()
        );
    }
}
