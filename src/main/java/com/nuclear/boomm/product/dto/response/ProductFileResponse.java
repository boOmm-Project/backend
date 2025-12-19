package com.nuclear.boomm.product.dto.response;

import com.nuclear.boomm.product.domain.ProductFile;

public record ProductFileResponse(
        String imageUrl
) {
    public static ProductFileResponse from(ProductFile productFile) {
        return new ProductFileResponse(
                productFile.getObjectKey()
        );
    }
}
