package com.sparta.blogproject.post.service;

import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.post.repository.PostRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    // TODO: UserRepository 선언


    public void createPost(PostRequestDto postRequestDto, Claims claims) {
        // TODO: UserRepository 생성 후 findByUsername

        // TODO: post에 postRequestDtor, user 받아서 저장하기
    }
}
