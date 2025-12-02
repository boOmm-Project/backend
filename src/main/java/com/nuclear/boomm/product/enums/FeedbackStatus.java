package com.nuclear.boomm.product.enums;

public enum FeedbackStatus {

    STAKEHOLDER_FEEDBACK_PENDING,               // 이해관계자 피드백 대기
    STAKEHOLDER_FEEDBACK_UPDATE,                // 이해관계자 피드백 업데이트
    APPROVAL,                                   // 승인
    COMPLIANCE_FEEDBACK_PENDING,                // 컴플라이언스 피드백 대기
    COMPLIANCE_FEEDBACK_UPDATE,                 // 컴플라이언스 피드백 업데이트
    RISK_REPORT_FEEDBACK_PENDING,               // 위험보고서 피드백 대기
    RISK_REPORT_FEEDBACK_UPDATE,                // 위험보고서 피드백 업데이트
    ADDITIONAL_EXPLANATION_REQUEST,             // 추가 설명 요청
    ADDITIONAL_EXPLANATION_UPDATE_PENDING,      // 추가설명 업데이트 대기
    RELEASE_APPROVAL                            // 출시 승인
}
