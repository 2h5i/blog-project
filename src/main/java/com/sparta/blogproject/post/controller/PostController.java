package com.sparta.blogproject.post.controller;

import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.dto.PostResponseDto;
import com.sparta.blogproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseEntity createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        postService.createPost(postRequestDto, userDetails);
        return ResponseEntity.ok("게시글 작성 완료");
    }

    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/api/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        postService.update(id, postRequestDto, userDetails);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(id, userDetails);
        return ResponseEntity.ok("게시글 삭제 완료");
    }
}
