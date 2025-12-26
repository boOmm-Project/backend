package com.nuclear.boomm.product.dto.request;

import com.nuclear.boomm.product.domain.ProductFile;
import jakarta.validation.constraints.NotBlank;

public record ProductFileRequest(
        @NotBlank
        String imageUrl,

        @NotBlank
        String originalFileName
) {
    public static ProductFileRequest from(ProductFile productFile) {
        return new ProductFileRequest(
                productFile.getUrl(),
                productFile.getOriginalFilename()
        );
    }
}
