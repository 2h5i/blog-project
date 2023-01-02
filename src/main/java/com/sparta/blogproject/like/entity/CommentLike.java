package com.sparta.blogproject.like.entity;

import com.sparta.blogproject.user.entity.User;
import com.sparta.blogproject.comment.entity.Comment;
import com.sparta.blogproject.common.entity.TimeStamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
@Builder
@NoArgsConstructor
public class CommentLike extends TimeStamped {

    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
