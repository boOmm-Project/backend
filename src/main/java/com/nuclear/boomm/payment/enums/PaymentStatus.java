package com.nuclear.boomm.payment.enums;

public enum PaymentStatus {
    PENDING,                // 처리 대기
    SUCCESS,                // 결제 성공
    FAILED,                 // 결제 실패
    CANCELLED              // 사용자가 취소
}
