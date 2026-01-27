package com.nuclear.boomm.caraccident.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InsuranceClaimStatus {
    WRITING_ACCIDENT("사고 내역 입력"),
    WRITING_UPLOAD("파일 업로드"),
    WRITING_SUBMIT("제출 완료"),

    RECEIPT_PROCESSING("접수 중"),
    RECEIPT_REQUEST_FIX("접수 보완 요청"),
    RECEIPT_REJECT("접수 거절"),
    RECEIPT_COMPLETE("접수 완료"),

    REVIEW_PROCESSING("심사 중"),
    REVIEW_REQUEST_FIX("심사 보완 요청"),
    REVIEW_REJECT("심사 거절"),
    REVIEW_COMPLETE("심사 완료");

    private final String description;

    public String getDescription() {
        return description;
    }
}
