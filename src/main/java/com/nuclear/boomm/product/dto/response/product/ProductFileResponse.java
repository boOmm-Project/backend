package com.nuclear.boomm.product.dto.response.product;

import com.nuclear.boomm.product.domain.ProductFile;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ProductFileResponse(
        @NotBlank
        String imageUrl,

        @NotBlank
        String fileName
) {
    public static ProductFileResponse from(ProductFile productFile) {
        return new ProductFileResponse(
                productFile.getUrl(),
                productFile.getOriginalFilename()
        );
    }

    public static List<ProductFileResponse> from(List<ProductFile> productFiles) {
        return productFiles.stream()
                .map(ProductFileResponse::from)
                .toList();
    }
}
