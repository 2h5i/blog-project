package com.sparta.blogproject.comment.controller;

import com.sparta.blogproject.comment.dto.CommentRequestDto;
import com.sparta.blogproject.comment.service.CommentService;
import com.sparta.blogproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class CommentController {
    private final CommentService commentService;

    //    작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(postId, commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok("작성 완료");
    }

//    수정
    @PutMapping("/comments/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(id, commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok("수정 완료");
    }

//    삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, userDetails.getUser());
        return ResponseEntity.ok("삭제 완료");
    }

}