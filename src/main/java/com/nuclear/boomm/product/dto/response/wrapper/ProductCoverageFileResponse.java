package com.nuclear.boomm.product.dto.response.wrapper;

import com.nuclear.boomm.product.dto.response.CoverageResponse;
import com.nuclear.boomm.product.dto.response.ProductFileResponse;
import com.nuclear.boomm.product.dto.response.ProductResponse;

import java.util.List;

public record ProductCoverageFileResponse(
        ProductResponse product,
        List<CoverageResponse> coverage,
        List<ProductFileResponse> file
) {}
