package com.nuclear.boomm.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),

    // 해당 상품이 없는 경우
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없거나 권한이 없습니다."),

    // 파일 업로드 실패
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    
    // 출시된 상품 삭제 시도 시
    PRODUCT_IS_RELEASED(HttpStatus.BAD_REQUEST, "출시된 상품은 삭제할 수 없습니다."),

    // 피드백 찾을 수 없음
    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 피드백은 찾을 수 없거나 권한이 없습니다."),

    // 입력 값 잘못됨
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력 값이 잘못되었습니다."),

    // 추가 설명 요청 찾을 수 없음
    EXTRA_DESCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 추가 설명을 찾을 수 없거나 권한이 없습니다."),

    // 위험 보고서 찾을 수 없음
    RISK_REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 위험 보고서를 찾을 수 없거나 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
