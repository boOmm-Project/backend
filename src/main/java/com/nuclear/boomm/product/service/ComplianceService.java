package com.nuclear.boomm.product.service;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.product.domain.RiskReport;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.repository.product.RiskReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComplianceService {

    private final RiskReportRepository riskReportRepository;

    @Transactional
    public Long permitRelease(Long userId, Long productId) {
        // 사용자 검증
        RiskReport riskReport = riskReportRepository.findByProduct_ProductIdAndComplianceId(productId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RISK_REPORT_NOT_FOUND));

        // 출시로 상태 변경
        riskReport.getProduct().release();

        // productId 반환
        return riskReport.getProduct().getProductId();
    }
}
