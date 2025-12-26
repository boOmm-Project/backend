package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(@NotNull Long aLong);

    Optional<Product> findByProductIdAndUserId(@NotNull Long productId, Long userId);

    List<Product> findAllByIsReleasedTrue();

    List<Product> findAllByIsReleasedFalse();
}
