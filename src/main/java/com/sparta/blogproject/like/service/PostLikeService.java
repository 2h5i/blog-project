package com.sparta.blogproject.like.service;

import com.sparta.blogproject.like.repository.CommentLikeRepository;
import com.sparta.blogproject.like.repository.PostLikeRepository;
import com.sparta.blogproject.like.dto.MsgResponseDto;
import com.sparta.blogproject.like.entity.PostLike;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.sparta.blogproject.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 게시글 좋아요 확인
    @Transactional(readOnly = true)
    public boolean checkPostLike(Long postId, User user) {
        return postLikeRepository.existsByPostIdAndUserId(postId, Long.valueOf(user.getUsername()));
    }

    // 게시글 좋아요 생성 및 삭제
    @Transactional
    public MsgResponseDto savePostLike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾는 게시글이 없습니다.")
        );

        if (!checkPostLike(postId, user)) {
            postLikeRepository.saveAndFlush(new PostLike(post, user));
            return new MsgResponseDto("좋아요 완료", HttpStatus.OK.value());
        } else {
            postLikeRepository.deleteByPostIdAndUserId(postId, Long.valueOf(user.getUsername()));
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}
