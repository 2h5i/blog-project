package com.sparta.blogproject.post.dto;

import com.sparta.blogproject.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createdAt;
    // TODO: List<CommentResponseDto> comments 추가하기

    public PostResponseDto(Post post) {
        this.username = post.getUser().getUsername();
        this.contents = post.getContents();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        // TODO: this.comments 추가하기
    }
}
