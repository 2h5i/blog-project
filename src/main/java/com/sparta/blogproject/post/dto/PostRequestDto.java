package com.sparta.blogproject.post.dto;

import com.sparta.blogproject.User.entity.User;
import com.sparta.blogproject.post.entity.Post;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String contents;
    private String title;
    private User user;
    private Post post;
}
