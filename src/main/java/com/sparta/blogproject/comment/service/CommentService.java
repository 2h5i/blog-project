package com.sparta.blogproject.comment.service;


import com.sparta.blogproject.comment.dto.CommentDto;
import com.sparta.blogproject.comment.repository.CommentRepository;
import com.sparta.blogproject.comment.entity.Comment;
import com.sparta.blogproject.common.jwt.JwtUtil;
import com.sparta.blogproject.user.dto.LoginRequest;
import com.sparta.blogproject.user.entity.User;
import com.sparta.blogproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long update(Long id, CommentDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        comment.update(requestDto);
        return comment.getId();
    }

}