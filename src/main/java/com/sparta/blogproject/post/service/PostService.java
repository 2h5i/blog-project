package com.sparta.blogproject.post.service;

import com.sparta.blogproject.post.dto.PostRequestDto;
import com.sparta.blogproject.post.dto.PostResponseDto;
import com.sparta.blogproject.post.entity.Post;
import com.sparta.blogproject.post.repository.PostRepository;
import com.sparta.blogproject.user.entity.User;
import com.sparta.blogproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
    public void createPost(PostRequestDto postRequestDto, UserDetails userDetails) {
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    public void update(Long id, PostRequestDto postRequestDto, UserDetails userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (post.getUser().getUsername().equals(userDetails.getUsername()) || userDetails.getAuthorities().equals("ROLE_ADMIN")) {
            post.update(postRequestDto);
            postRepository.save(post);
        }
    }

    public void deletePost(Long id, UserDetails userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (post.getUser().getUsername().equals(userDetails.getUsername()) || userDetails.getAuthorities().equals("ROLE_ADMIN")) {
            postRepository.delete(post);
        }
    }
}
