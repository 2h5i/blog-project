package com.sparta.blogproject.like.entity;

import com.sparta.blogproject.user.entity.User;
import com.sparta.blogproject.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;



import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class PostLike {


    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //boardLikeList : board 관계 --> 다대일 양방향 관계
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //생성자
    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}