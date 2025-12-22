package com.nuclear.boomm.product.dto.response;

import com.nuclear.boomm.product.domain.ProductFile;

import java.util.List;

public record ProductFileResponse(
        String imageUrl
) {
    public static ProductFileResponse from(ProductFile productFile) {
        return new ProductFileResponse(
                productFile.getObjectKey()
        );
    }

    public static List<ProductFileResponse> from(List<ProductFile> productFiles) {
        return productFiles.stream()
                .map(ProductFileResponse::from)
                .toList();
    }
}
