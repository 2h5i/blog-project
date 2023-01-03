package com.sparta.blogproject.comment.entity;

import com.sparta.blogproject.comment.dto.CommentRequestDto;
import com.sparta.blogproject.common.entity.TimeStamped;
import com.sparta.blogproject.like.entity.CommentLike;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //        게시글
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //        작성자
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String comments;

    @OneToMany(mappedBy = "comment", fetch = LAZY, cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, Post post, User user) {
        this.comments = commentRequestDto.getComments();
        this.post = post;
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comments = commentRequestDto.getComments();
    }

}