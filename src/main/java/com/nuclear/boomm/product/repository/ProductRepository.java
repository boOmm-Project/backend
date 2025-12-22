package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUserIdAndProductId(Long userId, @NotNull Long aLong);

    Optional<Product> findByProductId(@NotNull Long aLong);

    List<Product> findAllByProductIdAndIsDoneTrue(@NotNull Long aLong);

    Optional<Product> findByProductIdAndUserId(@NotNull Long aLong, Long userId);

    List<Product> findAllByIsDoneTrue();

    List<Product> findAllByIsDoneFalse();
}
