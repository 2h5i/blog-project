package com.sparta.blogproject.comment.service;


import com.sparta.blogproject.comment.dto.CommentRequestDto;
import com.sparta.blogproject.comment.dto.CommentResponseDto;
import com.sparta.blogproject.comment.entity.Comment;
import com.sparta.blogproject.comment.repository.CommentRepository;
import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.repository.PostRepository;
import com.sparta.blogproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;


    @Transactional
    public void createComment(CommentRequestDto commentRequestDto, User user) {
        Comment comment = new Comment(commentRequestDto, user);
        commentRepository.save(comment);
    }

    @Transactional
    public CommentResponseDto getCommentsByID(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (comment.getUser().getUsername().equals(user.getUsername())) {
            comment.update(commentRequestDto);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }
    }

    @Transactional
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (comment.getUser().getUsername().equals(user.getUsername())) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }
    }
}