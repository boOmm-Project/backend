package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.RiskReport;
import com.nuclear.boomm.product.dto.response.product.RiskReportResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.product.ProductRepository;
import com.nuclear.boomm.product.repository.product.RiskReportRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RiskReportServiceTests {
    @InjectMocks
    private RiskReportService riskReportService;

    @Mock
    private RiskReportRepository riskReportRepository;
    @Mock
    private ProductRepository productRepository;

    private Long productManagerId;
    private Long complianceId;

    private Long productId;
    private Long reportId;

    private RiskReport riskReport;
    private RiskReportResponse riskReportResponse;
    private BigDecimal lossRatioForecast;
    private String competitorProductComparison;

    private Product product;

    @BeforeEach
    void setUp() {
        productManagerId = 1L;
        complianceId = 10L;

        productId = 2L;
        reportId = 20L;

        lossRatioForecast = BigDecimal.valueOf(95.5);
        competitorProductComparison = "competitorProductComparison";

        product = Product.builder()
                .userId(productManagerId)
                .build();
        ReflectionTestUtils.setField(product, "productId", productId);

        riskReport = RiskReport.builder()
                .product(product)
                .lossRatioForecast(lossRatioForecast)
                .competitorProductComparison(competitorProductComparison)
                .build();
        ReflectionTestUtils.setField(riskReport, "reportId", reportId);
    }


    @Test
    @DisplayName("상품에 대한 위험 보고서 생성 - 성공")
    void createRiskReport_Success() {
        // given
        given(productRepository.findByProductId(productId)).willReturn(Optional.of(product));

        given(riskReportRepository.save(any(RiskReport.class))).willReturn(riskReport);

        // when
        RiskReportResponse response = riskReportService.createRiskReport(productManagerId, productId);

        // then
        assertEquals(reportId, response.reportId());
        assertEquals(productId, response.productId());
        assertEquals(lossRatioForecast, response.lossRatioForecast());
        assertEquals(competitorProductComparison, response.competitorProductComparison());

        verify(productRepository, times(1)).findByProductId(productId);
        verify(riskReportRepository, times(1)).save(any(RiskReport.class));
    }
    @Test
    @DisplayName("상품에 대한 위험 보고서 생성 - 실패 - PRODUCT_NOT_FOUND - 1")
    void createRiskReport_Failure_PRODUCT_NOT_FOUND_1() {
        // when
        CustomException exception = assertThrows(CustomException.class,
                () -> riskReportService.createRiskReport(productManagerId, productId));
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }
}