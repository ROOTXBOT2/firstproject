package com.firstproject.auth.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author rua
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    private String id;

    @NotBlank(message = "사용자명이 빈 값일 수 없습니다.")
    @Size(max = 20, message = "닉네임은 최대 20자까지 가능합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;
}
