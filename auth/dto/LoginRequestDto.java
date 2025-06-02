package com.firstproject.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


/**
 * @author rua
 */

@Getter
@Builder
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "ID는 빈 값일 수 없습니다.")
    private final String id;           // 회원 이메일 아이디

    @NotBlank(message = "패스워드는 빈 값일 수 없습니다.")
    private final String password;      // 회원 비밀번호

}
