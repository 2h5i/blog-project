package com.sparta.blogproject.comment.controller;

import com.sparta.blogproject.comment.dto.CommentDto;
import com.sparta.blogproject.comment.repository.CommentRepository;
import com.sparta.blogproject.comment.service.CommentService;
import com.sparta.blogproject.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

//    댓글 작성
    @PostMapping("/api/posts/comments")
    public Comment createComment(@RequestBody CommentDto requestDto) {
        Comment comment = new Comment(requestDto);
        System.out.println("작성 완료");
        return commentRepository.save(comment);
    }

//    댓글 조회
    @GetMapping("/api/posts/comments")
    public List<Comment> readComment() {
        return commentRepository.findAllByOrderByModifiedAtDesc();
    }

//    댓글 수정
    @PutMapping("/api/posts/comments/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentDto requestDto) {
        commentService.update(id, requestDto);
        System.out.println("수정되었습니다.");
        return id;
    }

//    댓글 삭제
    @DeleteMapping("/api/posts/comments/{id}")
    public Long deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        System.out.println("삭제되었습니다.");
        return id;
    }

}
