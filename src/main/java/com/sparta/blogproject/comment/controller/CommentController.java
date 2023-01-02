package com.sparta.blogproject.comment.controller;

import com.sparta.blogproject.comment.dto.CommentRequestDto;
import com.sparta.blogproject.comment.dto.CommentResponseDto;
import com.sparta.blogproject.comment.service.CommentService;
import com.sparta.blogproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{id}/comments")
public class CommentController {
    private final CommentService commentService;

//    작성
    @PostMapping("/")
    public ResponseEntity createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok("작성 완료");
    }

//    조회
    @GetMapping("/")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{commentID}")
    public CommentResponseDto getCommentById(@PathVariable Long id) {
        return commentService.getCommentsByID(id);
    }

//    수정
    @PutMapping("/{commentID}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(id, commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok("수정 완료");
    }

//    삭제
    @DeleteMapping("/{commentID}")
    public ResponseEntity deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, userDetails.getUser());
        return ResponseEntity.ok("삭제 완료");
    }

}
