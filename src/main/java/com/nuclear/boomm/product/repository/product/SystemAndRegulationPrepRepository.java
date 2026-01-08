package com.nuclear.boomm.product.repository.product;

import com.nuclear.boomm.product.domain.SystemAndRegulationPrep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAndRegulationPrepRepository extends JpaRepository<SystemAndRegulationPrep, Long> {
    void deleteAllByProductId(Long productId);
}
