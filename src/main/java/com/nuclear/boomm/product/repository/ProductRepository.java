package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    Optional<Product> findByUserId(String username);

    Optional<Product> findByUserIdAndProductId(Long userId, @NotNull Long aLong);
}
