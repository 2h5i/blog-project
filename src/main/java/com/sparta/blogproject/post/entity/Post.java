package com.sparta.blogproject.post.entity;

import com.sparta.blogproject.comment.dto.CommentResponseDto;
import com.sparta.blogproject.comment.entity.Comment;
import com.sparta.blogproject.common.entity.TimeStamped;
import com.sparta.blogproject.like.entity.PostLike;
import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.dto.PostResponseDto;
import com.sparta.blogproject.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // cascade 함께 삭제하도록 구현
//    @OrderBy("id asc") // id 순서대로 정렬
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostLike> PostLikeList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user) {
        this.contents = postRequestDto.getContents();
        this.title = postRequestDto.getTitle();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto) {
        this.contents = postRequestDto.getContents();
        this.title = postRequestDto.getTitle();
    }
}