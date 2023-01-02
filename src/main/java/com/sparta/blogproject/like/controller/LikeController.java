package com.sparta.blogproject.like.controller;

import com.sparta.blogproject.common.security.UserDetailsImpl;
import com.sparta.blogproject.like.dto.MsgResponseDto;
import com.sparta.blogproject.like.service.CommentLikeService;
import com.sparta.blogproject.like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;


    //게시글 좋아요
    @PostMapping("/post/{postId}")
    public  ResponseEntity<MsgResponseDto> savePostLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok().body(postLikeService.savePostLike(postId, userDetails.getUser()));
    }

    //댓글 좋아요
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<MsgResponseDto> commentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentLikeService.CommentLike(commentId, userDetails.getUser()));
    }
}

