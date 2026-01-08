package com.nuclear.boomm.product.repository.product;

import com.nuclear.boomm.product.domain.ProductFile;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductFileRepository extends JpaRepository<ProductFile, Long> {
    List<ProductFile> findAllByProductId(@NotNull Long productId);

    void deleteByProductId(@NotNull Long productId);

    void deleteAllByProductId(Long productId);

    Optional<ProductFile> findByOriginalFilename(String originalFilename);
}
