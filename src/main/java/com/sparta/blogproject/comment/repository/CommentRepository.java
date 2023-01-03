package com.sparta.blogproject.comment.repository;

import com.sparta.blogproject.comment.entity.Comment;
import com.sparta.blogproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();
}