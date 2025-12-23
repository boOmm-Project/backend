package com.nuclear.boomm.product.dto.request.wrapper;

import com.nuclear.boomm.product.dto.request.CoverageRequest;
import com.nuclear.boomm.product.dto.request.ProductRequest;

import java.util.List;

public record ProductCoverageRequest(
        ProductRequest product,
        List<CoverageRequest> coverage
) {}
