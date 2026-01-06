package com.nuclear.boomm.product.repository.product;

import com.nuclear.boomm.product.domain.RiskReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskReportRepository extends JpaRepository<RiskReport, Long> {
    void deleteAllByProductId(Long productId);
}
