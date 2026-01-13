package com.nuclear.boomm.product.repository.product;

import com.nuclear.boomm.product.domain.RiskReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskReportRepository extends JpaRepository<RiskReport, Long> {
    void deleteAllByProduct_ProductId(Long productId);

    Optional<RiskReport> findByReportId(Long reportId);

    Optional<RiskReport> findByProduct_ProductIdAndComplianceId(Long productId, Long userId);

    Optional<RiskReport> findByReportIdAndComplianceId(Long reportId, Long complianceId);

    boolean existsByReportIdAndComplianceId(Long reportId, Long complianceId);
}
