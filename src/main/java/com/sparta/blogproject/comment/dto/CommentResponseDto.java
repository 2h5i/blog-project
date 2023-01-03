package com.sparta.blogproject.comment.dto;

import com.sparta.blogproject.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String username;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int like;

    private Integer CommentLike;

    public CommentResponseDto(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.comments = comment.getComments();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

}