package com.sparta.blogproject.post.controller;

import com.sparta.blogproject.common.security.UserDetailsImpl;
import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.dto.PostResponseDto;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(postRequestDto, userDetails.getUser());
        return ResponseEntity.ok("게시글 작성 완료");
    }

    @GetMapping("")
    public Page<PostResponseDto> getPosts(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return postService.getPosts(pageable);
    }

    @GetMapping("/{id}")
    public PostResponseDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(id, postRequestDto, userDetails.getUser());
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok("게시글 삭제 완료");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/{id}")
    public ResponseEntity updatePostByAdmin(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        postService.updatePostByAdmin(id, postRequestDto);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity deletePostByAmin(@PathVariable Long id) {
        postService.deletePostByAdmin(id);
        return ResponseEntity.ok("게시글 삭제 완료");
    }
}
