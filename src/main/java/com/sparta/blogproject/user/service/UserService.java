package com.sparta.blogproject.user.service;


import com.sparta.blogproject.common.jwt.JwtUtil;
import com.sparta.blogproject.user.dto.LoginRequest;
import com.sparta.blogproject.user.dto.ResponseStatusDto;
import com.sparta.blogproject.user.dto.SignupRequest;
import com.sparta.blogproject.user.dto.StatusEnum;
import com.sparta.blogproject.user.entity.User;
import com.sparta.blogproject.user.entity.UserRoleEnum;
import com.sparta.blogproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public ResponseStatusDto signup(@Valid SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = passwordEncoder.encode(signupRequest.getPassword());

        //회원중복확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");//statuCode:400 에러메시지와 같이 반환
        }
        String email = signupRequest.getEmail();

        //사용자 Role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequest.isAdmin()) {
            if (!signupRequest.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role);

        userRepository.save(user);
        return new ResponseStatusDto(StatusEnum.SIGN_SUCCESS);

    }

    @Transactional // readOnly= true  하면 에러남
    public ResponseStatusDto login(LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //비밀번호 확인
          if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));


        return new ResponseStatusDto(StatusEnum.LOGIN_SUCCESS);
    }

}
