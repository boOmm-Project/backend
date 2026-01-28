package com.nuclear.boomm.underwriting.enums;

public enum RejectReason {
    MISSING_DOCUMENT,          // 서류누락
    POOR_HEALTH,               // 건강상태 불량
    HIGH_RISK_OCCUPATION,      // 고위험 직업군
    AGE_EXCEEDED,              // 연령 초과
    FREQUENT_ACCIDENT_HISTORY, // 과거 사고이력 과다
    BAD_CREDIT,                // 신용불량
    DEBT_DEFAULT,              // 채무불이행
    VEHICLE_BAD_CONDITION,     // 자동차-운행상태 불량
    DUI_HISTORY,               // 음주운전 이력 Driving Under the Influence
    HIGH_VALUE_VEHICLE        // 고가 차량 소유
    }
