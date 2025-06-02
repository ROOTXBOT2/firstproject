package com.firstproject.auth.restcontroller;

import com.firstproject.auth.dto.ApiResponse;
import com.firstproject.auth.dto.LoginRequestDto;
import com.firstproject.auth.dto.SignupRequestDto;
import com.firstproject.auth.entity.Users;
import com.firstproject.auth.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rua
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainApiController {
    private final UserService userService;

    /** 회원 가입 **/
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequestDto req) {
        if(userService.register(req)){
            return ResponseEntity.ok(new ApiResponse(true, "회원가입이 완료되었습니다."));
        }
        return ResponseEntity.ok(new ApiResponse(false, "회원가입이 실패했습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequestDto req, HttpSession session) {
        // 1) 인증 로직
        Users user = userService.authenticate(req);

        // 2) 세션에 사용자 ID 저장
        session.setAttribute("loginUserId", user.getUserId());
        // (옵션) nickname을 세션에 보관해두면, 화면에서 사용자 이름 표시할 때 편리합니다.
        session.setAttribute("loginUserNickname", user.getNickname());

        // 스프링이 자동으로 Set-Cookie: JSESSIONID=… 헤더를 붙여 줍니다.
        return ResponseEntity.ok(new ApiResponse(true, "로그인 성공"));
    }
}
