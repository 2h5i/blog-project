package com.sparta.blogproject.post.controller;

import com.sparta.blogproject.common.jwt.JwtUtil;
import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.post.service.PostService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;

    @PostMapping("/api/posts")
    public ResponseEntity createPost(@RequestBody PostRequestDto postRequestDto) {
        String username = postRequestDto.getUser().getUsername;
        postService.createPost(postRequestDto);
        Post post = new Post(postRequestDto, user);
        return ResponseEntity.ok("게시글 작성 완료");
    }

    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }
}
