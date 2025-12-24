package com.nuclear.boomm.product.dto.request;

import com.nuclear.boomm.product.domain.ProductFile;

public record ProductFileRequest(
        String imageUrl,
        String originalFileName
) {
    public static ProductFileRequest from(ProductFile productFile) {
        return new ProductFileRequest(
                productFile.getUrl(),
                productFile.getOriginalFilename()
        );
    }
}
