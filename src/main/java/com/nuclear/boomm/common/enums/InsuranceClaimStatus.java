package com.nuclear.boomm.common.enums;

public enum InsuranceClaimStatus {
    RECEIVED, // 접수대기

    RECEIPT_REVIEW, // 접수 검토중

    RECEIPT_COMPLEMENT, // 접수 보완요청

    RECEIPT_COMPLETE, // 접수 완료

    REVIEW_PENDING, // 심사 대기

    REVIEW_IN_PROGRESS, // 심사 진행중

    REVIEW_COMPLEMENT, // 심사 보완요청

    REVIEW_REJECT, // 심사 거절

    REVIEW_COMPLETE // 심사 완료
}
