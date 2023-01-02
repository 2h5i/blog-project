package com.sparta.blogproject.common.handler.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int statusCode;
    private String errorMsg;

    public ErrorResponse(ErrorCode errorCode, String errorMsg) {
        this.statusCode = errorCode.getStatusCode();
        this.errorMsg = errorMsg;
    }

    public static ErrorResponse of(ErrorCode errorCode, String errorMsg) {
        return new ErrorResponse(errorCode , errorMsg);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode, errorCode.getErrorMsg());
    }
}
