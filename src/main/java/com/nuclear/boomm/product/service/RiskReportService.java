package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.domain.RiskReport;
import com.nuclear.boomm.product.dto.response.product.RiskReportResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import com.nuclear.boomm.product.repository.product.ProductRepository;
import com.nuclear.boomm.product.repository.product.RiskReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiskReportService {
    private final RiskReportRepository riskReportRepository;
    private final ProductRepository productRepository;

    /**
     * 상품에 대한 위험 보고서 생성
     *
     * @param userId    상품 관리자 고유 번호
     * @param productId 상품 고유 번호
     * @return RiskReportResponse    Dto로 변환된 RiskReport
     * @throws CustomException(ErrorCode.PRODUCT_NOT_FOUND) 해당 상품이 상품 관리자가 생성한 상품이 아닐 경우, 해당 번호의 상품이 없을 경우
     */
    @Transactional(rollbackFor = Exception.class)
    public RiskReportResponse createRiskReport(Long userId, Long productId) {
        // 전달받은 productId로 Product 조회
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // 해당 상품 관리자가 상품의 생성자가 아니라면 예외
        if (!product.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // RiskReport 생성 및 저장 후 반환
        return RiskReportResponse.from(riskReportRepository.save(
                RiskReport.builder()
                        .product(product)
                        .build()
        ));
    }
}
