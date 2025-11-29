package com.nuclear.boomm.payment.enums;

public enum FailureMessage {

    INSUFFICIENT_FUNDS,         // 잔액 부족
    CARD_EXPIRED,               // 카드 만료
    NETWORK_ERROR,              // 네트워크 오류
    INVALID_ACCOUNT             // 계좌 정보 오류
}
