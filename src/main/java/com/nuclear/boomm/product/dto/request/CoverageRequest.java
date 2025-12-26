package com.nuclear.boomm.product.dto.request;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.enums.CoverageCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoverageRequest(
        Long id,

        @NotNull
        CoverageCategory category,

        @NotNull
        Long productId,

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        Double minCoverageLimit ,

        @NotNull
        Double maxCoverageLimit ,

        @NotNull
        Boolean isMandatory,

        @NotBlank
        String damageCalStandard
){
    public static CoverageRequest from(Coverage coverage) {
        return new CoverageRequest(
                coverage.getCoverageId(),
                coverage.getCategory(),
                coverage.getProductId(),
                coverage.getTitle(),
                coverage.getDescription(),
                coverage.getMinCoverageLimit(),
                coverage.getMaxCoverageLimit(),
                coverage.isMandatory(),
                coverage.getDamageCalStandard()
        );
    }

    public static Coverage toEntity(CoverageRequest request) {
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
