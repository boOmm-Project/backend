package com.nuclear.boomm.product.dto.request.wrapper;

import com.nuclear.boomm.product.dto.request.product.CoverageRequest;
import com.nuclear.boomm.product.dto.request.product.ProductRequest;

import java.util.List;

public record ProductCoverageRequest(
        ProductRequest product,
        List<CoverageRequest> coverage
) {}
