package com.sparta.blogproject.user.controller;

import com.sparta.blogproject.user.dto.LoginRequest;
import com.sparta.blogproject.user.dto.ResponseStatusDto;
import com.sparta.blogproject.user.dto.SignupRequest;

import com.sparta.blogproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

//        - 1. 회원 가입 API
//    - username, password를 Client에서 전달받기
//    - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
//  - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글 수정 / 삭제 가능
    @PostMapping("/signup")
    public ResponseStatusDto signup(@RequestBody @Valid SignupRequest signupRequest){
        return userService.signup(signupRequest);
    }

//- 2. 로그인 API
//    - username, password를 Client에서 전달받기
//    - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
//    - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고,
//    발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태 코드 와 함께 Client에


    @PostMapping("/login")
    public ResponseStatusDto login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest, response);
        //또는 return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto,response)); 간단히 표시가능

    }



}
