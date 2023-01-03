package com.sparta.blogproject.like.service;

import com.sparta.blogproject.comment.entity.Comment;
import com.sparta.blogproject.comment.repository.CommentRepository;
import com.sparta.blogproject.like.dto.MsgResponseDto;
import com.sparta.blogproject.like.entity.CommentLike;
import com.sparta.blogproject.like.entity.PostLike;
import com.sparta.blogproject.like.repository.CommentLikeRepository;
import com.sparta.blogproject.like.repository.PostLikeRepository;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.post.repository.PostRepository;
import com.sparta.blogproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;


    //댓글 좋아요
    @Transactional
    public MsgResponseDto CommentLike(Long commentId, User user) {
        //DB 에서 댓글을 찾아봄
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("찾는 댓글이 없습니다.")
        );
        //DB 에 해당 user 가 '댓글 좋아요' 누른 적이 없다면, '댓글 좋아요' 를 추가하기
        if (commentLikeRepository.findByCommentIdAndUserId(commentId, Long.valueOf(user.getUsername())).isEmpty()){
            CommentLike commentLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentLikeRepository.save(commentLike);
            return new MsgResponseDto("좋아요 완료", HttpStatus.OK.value());
            //DB 에 해당 user 가 '댓글 좋아요' 누른 적이 있다면, '댓글 좋아요' 를 제거하기
        }else {
            commentLikeRepository.deleteByCommentIdAndUserId(comment.getId(), Long.valueOf(user.getUsername()));
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }

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
