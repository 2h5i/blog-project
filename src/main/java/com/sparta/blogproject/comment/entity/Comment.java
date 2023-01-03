package com.sparta.blogproject.comment.entity;

import com.sparta.blogproject.comment.dto.CommentRequestDto;
import com.sparta.blogproject.common.entity.TimeStamped;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //        게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //        작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String comments;

    public Comment(CommentRequestDto commentRequestDto, Post post, User user) {
        this.comments = commentRequestDto.getComments();
        this.post = post;
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comments = commentRequestDto.getComments();
    }

}