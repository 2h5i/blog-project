package com.sparta.blogproject.comment.service;

import com.sparta.blogproject.comment.dto.CommentRequestDto;
import com.sparta.blogproject.comment.entity.Comment;
import com.sparta.blogproject.comment.repository.CommentRepository;

import com.sparta.blogproject.post.entity.Post;

import com.sparta.blogproject.post.repository.PostRepository;
import com.sparta.blogproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    //    작성

    @Transactional
    public void createComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment parent = null;
        //자식 댓글인 경우
        if (commentRequestDto.getParentId() != null) {
            parent = commentRepository.findById(commentRequestDto.getParentId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
            );
            // 부모 댓글과 자식 댓글의 게시글 아이디가 같은지 확인
            if (!parent.getMainPostId().equals(postId)) {
                throw new IllegalArgumentException("게시글 번호가 일치하지 않습니다.");
            }
        }

        //댓글인 경우
        if (parent == null) {
            Comment comment = new Comment(commentRequestDto, post, user, parent);
            commentRepository.save(comment);
        //대댓글인 경우
        } else {
            Comment comment = new Comment(commentRequestDto, post, user, parent);
            comment.setMainPostId(post.getId());
            comment.getParent().setId(commentRequestDto.getParentId());
            commentRepository.save(comment);
        }

    }

    //    수정
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

    //    삭제
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