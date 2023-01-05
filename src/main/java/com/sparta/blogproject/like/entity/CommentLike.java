package com.sparta.blogproject.like.entity;

import com.sparta.blogproject.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.blogproject.user.entity.User;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
@Builder
@NoArgsConstructor
public class CommentLike {

    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentLikeId;

    //commentLike : comment  관계 --> 다대일 단방향 관계
    @ManyToOne
    @JoinColumn(name = "comment_id")        //테이블에서 name 속성을 따로 적어주지 않는 경우, name 은 해당 객체명이 된다.
    private Comment comment;

    //commentLike : user  관계 --> 다대일 단방향 관계

    @ManyToOne
    @JoinColumn(name = "comment_like_user_id")      //테이블에서 name 속성을 따로 적어주지 않는 경우, name 은 해당 객체명이 된다.
    private User user;
}