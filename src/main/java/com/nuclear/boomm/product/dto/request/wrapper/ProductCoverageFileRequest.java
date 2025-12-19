package com.nuclear.boomm.product.dto.request.wrapper;

import com.nuclear.boomm.product.dto.request.CoverageRequest;
import com.nuclear.boomm.product.dto.request.ProductFileRequest;
import com.nuclear.boomm.product.dto.request.ProductRequest;
import com.nuclear.boomm.product.dto.response.CoverageResponse;
import com.nuclear.boomm.product.dto.response.ProductFileResponse;
import com.nuclear.boomm.product.dto.response.ProductResponse;

import java.util.List;

public record ProductCoverageFileRequest(
        Long stakeholderId,
        ProductRequest product,
        List<CoverageRequest> coverage,
        List<ProductFileRequest> file
) {}
