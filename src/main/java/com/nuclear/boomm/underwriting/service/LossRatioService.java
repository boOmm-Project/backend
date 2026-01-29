package com.nuclear.boomm.underwriting.service;

import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.repository.DraftContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LossRatioService {

    private final DraftContractRepository draftContractRepository;

    public Map<String, Object> getForm() {
        return Map.of(
                "fields", new String[]{"보험상품종류", "상품이름", "계약금액", "계약기간"},
                "hint", "손해율 분석에 필요한 기본 입력 항목입니다."
        );
    }

    public Map<String, Object> getContext(Long underwritingReviewId, Long contractId) {
        DraftContract draft = (contractId == null)
                ? null
                : draftContractRepository.findById(contractId).orElse(null);

        return Map.of(
                "underwritingReviewId", underwritingReviewId,
                "contractId", contractId,
                "draftContract", draft
        );
    }

    @Transactional
    public Map<String, Object> analyze(Map<String, Object> body) {
        // 실제 손해율 로직 대신 “동작 가능한 예시”
        BigDecimal expectedPayment = new BigDecimal("1200000"); // 예상 지급금
        BigDecimal premium = new BigDecimal("1500000");         // 예상 납부금

        BigDecimal lossRatio = expectedPayment
                .divide(premium, 4, RoundingMode.HALF_UP) // 0.8000
                .multiply(new BigDecimal("100"));         // 80.00

        return Map.of(
                "message", "손해율분석이완료되었습니다.",
                "expectedPayment", expectedPayment,
                "premium", premium,
                "lossRatioPercent", lossRatio
        );
    }
}
