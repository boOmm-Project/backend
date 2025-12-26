package com.nuclear.boomm.product.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),

    // 해당 상품이 없는 경우
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),

    // 파일 업로드 실패
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    
    // 출시된 상품 삭제 시도 시
    PRODUCT_IS_RELEASED(HttpStatus.BAD_REQUEST, "출시된 상품은 삭제할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
