package com.nuclear.boomm.product.service;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.domain.RiskReport;
import com.nuclear.boomm.product.dto.request.product.RiskReportUpdateRequest;
import com.nuclear.boomm.product.dto.response.product.RiskReportDetailResponse;
import com.nuclear.boomm.product.dto.response.product.RiskReportResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.repository.product.ProductFileRepository;
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
import java.util.List;
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
    @Mock
    private ProductFileRepository productFileRepository;

    private Long productManagerId;
    private Long complianceId;

    private Long productId;
    private Long reportId;

    private RiskReport riskReport;
    private RiskReportResponse riskReportResponse;
    private BigDecimal lossRatioForecast;
    private String competitorProductComparison;
    private RiskReportUpdateRequest riskReportUpdateRequest;

    private Product product;

    private List<ProductFile> productFileList;

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
                .complianceId(complianceId)
                .build();
        ReflectionTestUtils.setField(riskReport, "reportId", reportId);

        productFileList = List.of(
                ProductFile.builder()
                        .url("url")
                        .originalFilename("originalFilename")
                        .uuidName("uuidName")
                        .extension("extension")
                        .contentType("contentType")
                        .fileSize(1L)
                        .uploaderId(productManagerId)
                        .productId(productId)
                        .build()
        );
        ReflectionTestUtils.setField(productFileList.get(0), "fileId", 1L);

        riskReportUpdateRequest = new RiskReportUpdateRequest(
                competitorProductComparison,
                lossRatioForecast
        );
    }


    @Test
    @DisplayName("상품에 대한 위험 보고서 생성 - 성공")
    void createRiskReport_Success() {
        // given
        given(productRepository.findByProductId(productId)).willReturn(Optional.of(product));

        given(riskReportRepository.save(any(RiskReport.class))).willReturn(riskReport);

        // when
        RiskReportResponse response = riskReportService.createRiskReport(productManagerId, productId, complianceId);

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
                () -> riskReportService.createRiskReport(productManagerId, productId, complianceId));
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }


    @Test
    @DisplayName("상품에 대한 위험 보고서 세부사항 조회 - 성공")
    void getRiskReportDetails_Success() {
        // given
        given(riskReportRepository.findByReportId(reportId)).willReturn(Optional.of(riskReport));

        given(productFileRepository.findAllByProductId(productId)).willReturn(productFileList);

        // when
        RiskReportDetailResponse response = riskReportService.getRiskReportDetails(productManagerId, reportId, complianceId);

        // then
        assertEquals(reportId, response.reportId());
        assertEquals(lossRatioForecast, response.lossRatioForecast());
        assertEquals(competitorProductComparison, response.competitorProductComparison());
        assertEquals("url", response.fileResponseList().get(0).imageUrl());
        assertEquals("originalFilename", response.fileResponseList().get(0).fileName());
    }
    @Test
    @DisplayName("상품에 대한 위험 보고서 세부사항 조회 - 실패 - PRODUCT_NOT_FOUND")
    void getRiskReportDetails_Failure_PRODUCT_NOT_FOUND() {
        // given
        Product product = Product.builder()
                .userId(99L)
                .build();

        RiskReport report = RiskReport.builder()
                .product(product)
                .build();

        given(riskReportRepository.findByReportId(reportId))
                .willReturn(Optional.of(report));

        // when & then
        CustomException exception = assertThrows(CustomException.class, () ->
                riskReportService.getRiskReportDetails(productManagerId, reportId, complianceId)
        );
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }


    @Test
    @DisplayName("위험 보고서 피드백 반영 - 성공")
    void updateRiskReport_Success() {
        // given
        given(riskReportRepository.findByReportId(reportId)).willReturn(Optional.of(riskReport));

        // when
        RiskReportResponse response = riskReportService.updateRiskReport(productManagerId, reportId, riskReportUpdateRequest);

        // then
        assertEquals(reportId, response.reportId());
        assertEquals(productId, response.productId());
        assertEquals(lossRatioForecast, response.lossRatioForecast());
        assertEquals(competitorProductComparison, response.competitorProductComparison());

        verify(riskReportRepository, times(1)).findByReportId(reportId);
    }
    @Test
    @DisplayName("위험 보고서 피드백 반영 - 실패 - RISK_REPORT_NOT_FOUND")
    void updateRiskReport_Failure_RISK_REPORT_NOT_FOUND() {
        // when & then
        CustomException exception = assertThrows(CustomException.class, () ->
                riskReportService.updateRiskReport(productManagerId, reportId, riskReportUpdateRequest)
        );
        assertEquals(ErrorCode.RISK_REPORT_NOT_FOUND, exception.getErrorCode());
    }
    @Test
    @DisplayName("위험 보고서 피드백 반영 - 실패 - PRODUCT_NOT_FOUND")
    void updateRiskReport_Failure_RISK_PRODUCT_NOT_FOUND() {
        // given
        given(riskReportRepository.findByReportId(reportId)).willReturn(Optional.of(riskReport));

        Product product = Product.builder()
                .userId(99L)
                .build();

        RiskReport report = RiskReport.builder()
                .product(product)
                .build();

        given(riskReportRepository.findByReportId(reportId)).willReturn(Optional.of(report));

        // when & then
        CustomException exception = assertThrows(CustomException.class, () ->
                riskReportService.updateRiskReport(productManagerId, reportId, riskReportUpdateRequest)
        );
        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }
}