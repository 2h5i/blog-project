package com.sparta.blogproject.comment.entity;

import com.sparta.blogproject.comment.dto.CommentRequestDto;
import com.sparta.blogproject.common.entity.TimeStamped;
import com.sparta.blogproject.like.entity.CommentLike;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //        게시글
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column
    private Long mainPostId;

    //        작성자
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String comments;

    @OneToMany(mappedBy = "comment", fetch = LAZY, cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, Post post, User user, Comment parent) {
        this.comments = commentRequestDto.getComments();
        this.post = post;
        this.user = user;
        this.parent = parent;
        this.mainPostId = post.getId();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comments = commentRequestDto.getComments();
    }
}