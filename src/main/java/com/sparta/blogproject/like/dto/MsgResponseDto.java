package com.sparta.blogproject.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MsgResponseDto {
    //필드
    private String msg;
    private int statusCode;
}