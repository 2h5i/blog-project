package com.sparta.blogproject.like.entity;



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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String username, String comments) {
        this.username = username;
        this.comments = comments;
    }

//    public Comment(CommentDto requestDto) {
//        this.username = requestDto.getUsername();
//        this.comments = requestDto.getComments();
//    }
//
//    public void update(CommentDto requestDto) {
//        this.username = requestDto.getUsername();
//        this.comments = requestDto.getComments();
//    }

}