package com.nuclear.boomm.product.dto.response;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.dto.request.CoverageRequest;

import java.util.List;

public record CoverageResponse(
    Long id,
    String title,
    String description,
    double minCoverageLimit,
    double maxCoverageLimit,
    boolean isMandatory
) {
    public static CoverageResponse from(Coverage coverage) {
        return new CoverageResponse(
                coverage.getCoverageId(),
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

    public static Coverage from(CoverageRequest request) {
        return Coverage.builder()
                .coverageId(request.id())
                .category(request.category())
                .productId(request.productId())
                .title(request.title())
                .description(request.description())
                .minCoverageLimit(request.minCoverageLimit())
                .maxCoverageLimit(request.maxCoverageLimit())
                .isMandatory(request.isMandatory())
                .damageCalStandard(request.damageCalStandard())
                .build();
    }
}
