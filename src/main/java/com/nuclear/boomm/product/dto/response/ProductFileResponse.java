package com.nuclear.boomm.product.dto.response;

import com.nuclear.boomm.product.domain.ProductFile;

import java.util.List;

public record ProductFileResponse(
        String imageUrl,
        String fileName
) {
    public static ProductFileResponse from(ProductFile productFile) {
        return new ProductFileResponse(
                "http://dev.macacolabs.site:9000/product-files/" + productFile.getUrl(),
                productFile.getOriginalFilename()
        );
    }

    public static List<ProductFileResponse> from(List<ProductFile> productFiles) {
        return productFiles.stream()
                .map(ProductFileResponse::from)
                .toList();
    }
}
