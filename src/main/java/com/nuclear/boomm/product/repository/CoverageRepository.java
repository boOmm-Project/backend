package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.Coverage;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoverageRepository extends JpaRepository<Coverage, Long> {
    List<Coverage> findAllByProductId(@NotNull Long productId);
}
