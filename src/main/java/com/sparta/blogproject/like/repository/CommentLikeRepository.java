package com.sparta.blogproject.like.repository;

import com.sparta.blogproject.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long ccommentId, Long userid);
    void deleteByCommentIdAndUserId(Long ccommentId, Long userid);
    int countAllByCommentId(Long ccommentId);
}
