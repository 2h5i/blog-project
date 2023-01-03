package com.sparta.blogproject.user.controller;

import com.sparta.blogproject.common.security.UserDetailsImpl;
import com.sparta.blogproject.user.dto.LoginRequest;
import com.sparta.blogproject.user.dto.ResponseStatusDto;
import com.sparta.blogproject.user.dto.SignupRequest;

import com.sparta.blogproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // - 1. 회원 가입 API
    @PostMapping("/signup")
    public ResponseStatusDto signup(@RequestBody @Valid SignupRequest signupRequest) {
        return userService.signup(signupRequest);
    }

    //- 2. 로그인 API
    @PostMapping("/login")
    public ResponseStatusDto login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest, response);
    }
   //- 3. 회원탈퇴 API
    @DeleteMapping("/resign/{id}")
    public ResponseStatusDto resignMembership(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
          return userService.resignMembership(id,userDetails.getUser());
    }

}
