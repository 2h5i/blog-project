package com.sparta.blogproject.post.service;

import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.dto.PostResponseDto;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.post.repository.PostRepository;
import com.sparta.blogproject.user.entity.User;
import com.sparta.blogproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public void updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (post.getUser().getUsername().equals(user.getUsername())) {
            post.update(postRequestDto);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (post.getUser().getUsername().equals(user.getUsername())) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }
    }
}
