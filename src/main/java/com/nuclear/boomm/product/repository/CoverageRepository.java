package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.Coverage;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoverageRepository extends JpaRepository<Coverage, Long> {
    List<Coverage> findAllByProductId(@NotNull Long productId);

    void deleteAllByProductId(@NotNull Long productId);

    List<Coverage> findByProductIdAndCoverageIdIn(Long productId, List<Long> coverageIds);

    void deleteAllByProductIdAndCoverageIdNotIn(Long productId, List<Long> coverageIds);
}
