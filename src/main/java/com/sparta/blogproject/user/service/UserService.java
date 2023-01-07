package com.sparta.blogproject.user.service;

import com.sparta.blogproject.common.jwt.JwtUtil;
import com.sparta.blogproject.user.dto.*;
import com.sparta.blogproject.user.entity.User;
import com.sparta.blogproject.user.entity.UserRoleEnum;
import com.sparta.blogproject.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public ResponseStatusDto login(LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.updateRefreshToken(jwtUtil.createRefreshToken());
        userRepository.saveAndFlush(user);
        addTokenToHeader(response, user);

        return new ResponseStatusDto(StatusEnum.LOGIN_SUCCESS);
    }

    @Transactional
    public void reIssue(TokenRequestDto tokenRequestDto, HttpServletResponse response) {
        if(!jwtUtil.validateTokenExceptExpiration(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        User user = findUserByToken(tokenRequestDto);

        if(!user.getRefreshToken().equals(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        String refreshToken = jwtUtil.createRefreshToken();

        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);

        addTokenToHeader(response, user);
    }

    @Transactional
    public ResponseStatusDto resignMembership(Long id, User user) {
        User foundUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다. ")
        );
        if (foundUser.getUsername().equals(user.getUsername())) {
            userRepository.delete(foundUser);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }
        return new ResponseStatusDto(StatusEnum.RESIGN_SUCCESS);
    }

    private User findUserByToken(TokenRequestDto tokenRequestDto) {
        Claims claims = jwtUtil.getUserInfoFromToken(tokenRequestDto.getAccessToken().substring(7));
        String username = claims.getSubject();
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );
    }

    private void addTokenToHeader(HttpServletResponse response, User user) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        response.addHeader(JwtUtil.REFRESH_HEADER, user.getRefreshToken());
    }
}
