package com.sparta.blogproject.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String comments;
    private Long parentId;
}