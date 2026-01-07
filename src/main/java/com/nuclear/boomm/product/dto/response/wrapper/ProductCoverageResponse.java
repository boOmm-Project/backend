package com.nuclear.boomm.product.dto.response.wrapper;

import com.nuclear.boomm.product.domain.Coverage;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.dto.response.product.CoverageResponse;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;

import java.util.List;

public record ProductCoverageResponse(
        ProductResponse product,
        List<CoverageResponse> coverage
) {
    public static ProductCoverageResponse from(Product product, List<Coverage> coverages) {
        return new ProductCoverageResponse(
                ProductResponse.from(product),
                CoverageResponse.from(coverages)
        );
    }
}
