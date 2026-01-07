package com.nuclear.boomm.product.dto.response.wrapper;

import com.nuclear.boomm.product.dto.response.product.CoverageResponse;
import com.nuclear.boomm.product.dto.response.product.ProductFileResponse;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;

import java.util.List;

public record ProductCoverageFileResponse(
        ProductResponse productResponse,
        List<CoverageResponse> coverageResponses,
        List<ProductFileResponse>  productFileResponses
) {
}
