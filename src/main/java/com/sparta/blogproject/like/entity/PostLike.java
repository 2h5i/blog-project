package com.sparta.blogproject.like.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class PostLike {

    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //boardLikeList : board 관계 --> 다대일 양방향 관계
    @ManyToOne
    @JoinColumn(name = "Post_ID", nullable = false)

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    //생성자
    public BoardLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}