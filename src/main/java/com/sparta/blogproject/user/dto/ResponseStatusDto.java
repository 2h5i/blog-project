package com.sparta.blogproject.user.dto;

import lombok.Getter;

@Getter
public class ResponseStatusDto {

    private int StatusCode;
    private String msg;

    public ResponseStatusDto(StatusEnum status) {
        this.StatusCode = status.getStatusCode();
        this.msg = status.getMsg();
    }
}
