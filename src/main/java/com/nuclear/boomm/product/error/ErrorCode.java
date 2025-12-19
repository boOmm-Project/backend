package com.nuclear.boomm.product.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 404 NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 기획중인 상품이 없습니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다.");

    private HttpStatus status;
    private String message;
}
