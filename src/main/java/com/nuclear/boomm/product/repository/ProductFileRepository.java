package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.ProductFile;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFileRepository extends JpaRepository<ProductFile, Long> {
    List<ProductFile> findAllByProductId(@NotNull Long productId);
}
