package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.RiskReport;
import com.nuclear.boomm.product.repository.product.RiskReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ComplianceServiceTests {
    @InjectMocks
    private ComplianceService complianceService;

    @Mock
    private RiskReportRepository riskReportRepository;

    @Test
    @DisplayName("출시 승인 - 성공")
    void permitRelease_Success() {
        // given
        Long complianceId = 1L;
        Long productId = 2L;
        Long reportId = 3L;

        Product product = Product.builder()
                .isDone(true)
                .build();
        ReflectionTestUtils.setField(product, "productId", productId);

        RiskReport riskReport = RiskReport.builder()
                .product(product)
                .lossRatioForecast(BigDecimal.valueOf(100))
                .complianceId(complianceId)
                .competitorProductComparison("competitor")
                .build();
        ReflectionTestUtils.setField(riskReport, "reportId", reportId);

        given(riskReportRepository.findByProduct_ProductIdAndComplianceId(productId, complianceId)).willReturn(Optional.of(riskReport));

        // when
        Long response = complianceService.permitRelease(complianceId, productId);

        // then
        assertEquals(productId, response);
        assertTrue(riskReport.getProduct().isReleased());
    }
}