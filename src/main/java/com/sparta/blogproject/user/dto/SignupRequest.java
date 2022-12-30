package com.sparta.blogproject.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 8)
    @Pattern(regexp = "^[a-z0-9]*S", message = "최소 4자 이상, 10자 이하이며 a-z, 0-9 만 입력하세요.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "최소 8자 이상, 15자 이하이며 a-z, A-Z, 0-9 만 입력하세요.")
    private String password;

    //private String email;

    private boolean admin = false;
    private String adminToken = "";

}
