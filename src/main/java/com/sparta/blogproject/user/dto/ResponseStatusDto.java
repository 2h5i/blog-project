package com.sparta.blogproject.user.dto;

public class ResponseStatusDto {

    private int StatusCode;
    private String msg;

    public ResponseStatusDto(StatusEnum status) {
        this.StatusCode = status.getStatusCode();
        this.msg = status.getMsg();
    }
}
