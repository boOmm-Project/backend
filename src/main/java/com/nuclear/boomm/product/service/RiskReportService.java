package com.nuclear.boomm.product.service;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.product.domain.Feedback;
import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.domain.RiskReport;
import com.nuclear.boomm.product.dto.request.feedback.RiskReportFeedbackRequest;
import com.nuclear.boomm.product.dto.request.product.RiskReportUpdateRequest;
import com.nuclear.boomm.product.dto.response.feedback.FeedbackResponse;
import com.nuclear.boomm.product.dto.response.product.RiskReportDetailResponse;
import com.nuclear.boomm.product.dto.response.product.RiskReportResponse;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.repository.feedback.FeedbackRepository;
import com.nuclear.boomm.product.repository.product.ProductFileRepository;
import com.nuclear.boomm.product.repository.product.ProductRepository;
import com.nuclear.boomm.product.repository.product.RiskReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RiskReportService {
    private final RiskReportRepository riskReportRepository;
    private final ProductRepository productRepository;
    private final ProductFileRepository productFileRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional(rollbackFor = Exception.class)
    public RiskReportResponse createRiskReport(Long userId, Long productId, Long complianceId) {
        // 전달받은 productId로 Product 조회
        Product product = productRepository.findByProductIdAndUserId(productId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // RiskReport 생성 및 저장
        RiskReport report = riskReportRepository.save(RiskReport.create(product, complianceId));

        return RiskReportResponse.from(report);
    }

    public RiskReportDetailResponse getRiskReportDetails(Long userId, Long reportId) {
        RiskReport report = riskReportRepository.findByReportId(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.RISK_REPORT_NOT_FOUND));

        // 사용자가 볼 권한이 있는 사람(해당 상품 관리자, 해당 컴플라이언스)인지 검증
        if (!report.getProduct().getUserId().equals(userId) && !report.getComplianceId().equals(userId)) {  // 상품 관리자나 컴플라이언스가 아닌 경우
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // 위험 보고서에 속한 파일 데이터 리스트로 가져오기
        List<ProductFile> fileList = productFileRepository.findAllByProductId(report.getProduct().getProductId());

        return RiskReportDetailResponse.from(report, fileList);
    }

    @Transactional(rollbackFor = Exception.class)
    public RiskReportResponse updateRiskReport(Long userId, Long reportId, RiskReportUpdateRequest request) {
        // reportId로 위험 보고서 조회
        RiskReport report = riskReportRepository.findByReportId(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.RISK_REPORT_NOT_FOUND));
        Feedback reportFeedback = feedbackRepository.findByProduct_ProductIdAndRole(report.getProduct().getProductId(), "COMPLIANCE")
                .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND));

        // 위험 보고서의 상품에 대한 권한 확인
        if (!report.getProduct().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // 위험 보고서 피드백 상태 변경
        reportFeedback.updateStatus(FeedbackStatus.RISK_REPORT_FEEDBACK_UPDATE_PENDING);

        // 위험 보고서 업데이트 및 결과 반환
        return RiskReportResponse.from(report.update(request));
    }

    @Transactional(rollbackFor = Exception.class)
    public Long feedbackRiskReport(Long reportId, Long complianceId, Long feedbackId, RiskReportFeedbackRequest request) {
        // 사용자 검증
        Feedback feedback = feedbackRepository.findByFeedbackIdAndWriterId(feedbackId, complianceId)
                .orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_FOUND));

        // 피드백 업데이트
        feedback.updateDescription(request.description());
        feedback.updateStatus(FeedbackStatus.RISK_REPORT_FEEDBACK_PENDING);

        // 피드백 id 반환
        return feedback.getFeedbackId();
    }

    @Transactional(rollbackFor = Exception.class)
    public FeedbackResponse createRiskReportFeedback(Long complianceId, Long productId, Long reportId) {
        // 사용자 검증
        if (!riskReportRepository.existsByReportIdAndComplianceId(reportId, complianceId)) {
            throw new CustomException(ErrorCode.RISK_REPORT_NOT_FOUND);
        }

        // 피드백 생성
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        Feedback reportFeedback = Feedback.create(complianceId, product, "COMPLIANCE");
        feedbackRepository.save(reportFeedback);

        // 반환
        return FeedbackResponse.from(reportFeedback);
    }
}
