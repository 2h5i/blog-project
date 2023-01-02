package com.sparta.blogproject.comment.entity;

import com.sparta.blogproject.comment.dto.CommentDto;
import com.sparta.blogproject.common.entity.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comments;

    public Comment(String username, String comments) {
        this.username = username;
        this.comments = comments;
    }

    public Comment(CommentDto requestDto) {
        this.username = requestDto.getUsername();
        this.comments = requestDto.getComments();
    }

    public void update(CommentDto requestDto) {
        this.username = requestDto.getUsername();
        this.comments = requestDto.getComments();
    }

}