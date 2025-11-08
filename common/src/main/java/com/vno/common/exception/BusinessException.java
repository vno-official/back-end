package com.vno.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception dùng chung cho toàn bộ hệ thống microservices.
 * Đặt trong module `common` → không phụ thuộc Spring Context (chỉ dùng HttpStatus enum).
 * Dùng để trả về lỗi nghiệp vụ có mã code và HTTP status.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;

    // Constructor 1: message + BAD_REQUEST (mặc định)
    public BusinessException(String message) {
        this("BUSINESS_ERROR", message, HttpStatus.BAD_REQUEST);
    }

    // Constructor 2: code + message + BAD_REQUEST
    public BusinessException(String code, String message) {
        this(code, message, HttpStatus.BAD_REQUEST);
    }

    // Constructor 3: đầy đủ code, message, httpStatus
    public BusinessException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code != null ? code : "BUSINESS_ERROR";
        this.httpStatus = httpStatus != null ? httpStatus : HttpStatus.BAD_REQUEST;
    }

    // Constructor 4: message + cause + INTERNAL_SERVER_ERROR
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = "BUSINESS_ERROR";
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    // Constructor 5: code + message + cause + httpStatus
    public BusinessException(String code, String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.code = code != null ? code : "BUSINESS_ERROR";
        this.httpStatus = httpStatus != null ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}