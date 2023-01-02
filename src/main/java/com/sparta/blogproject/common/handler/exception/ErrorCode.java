package com.sparta.blogproject.common.handler.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ILLEGAL_ARGUMENT(400, "");

    private final int statusCode;
    private final String errorMsg;

    ErrorCode(int statusCode, String errorMsg) {
        this.statusCode = statusCode;
        this.errorMsg = errorMsg;
    }
}