package com.sparta.blogproject.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum StatusEnum {

    SIGN_SUCCESS(200, "회원가입 성공"),
    LOGIN_SUCCESS(200, "로그인 성공");
    int statusCode;
    String msg;

    StatusEnum(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
