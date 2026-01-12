package com.nuclear.boomm.product.repository.product;

import com.nuclear.boomm.product.domain.RiskReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskReportRepository extends JpaRepository<RiskReport, Long> {
    void deleteAllByProduct_ProductId(Long productId);

    Optional<RiskReport> findByProduct_ProductId(Long productId);

    Optional<RiskReport> findByReportId(Long reportId);
}
