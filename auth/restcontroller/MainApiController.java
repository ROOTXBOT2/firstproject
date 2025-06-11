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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author rua
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MainApiController {
    private final UserService userService;

    /** ID 중복 확인 **/
    @GetMapping("/check-id")
    public ResponseEntity<ApiResponse<Void>> checkId(@RequestParam("id") String id) {
        // DB에 해당 ID가 있는지 확인

        if (userService.idCheck(id)) {
            // 이미 있으면 사용 불가
            return ResponseEntity.ok(new ApiResponse<>(false, "이미 사용 중인 아이디입니다."));
        } else {
            // 없으면 사용 가능
            return ResponseEntity.ok(new ApiResponse<>(true, "사용 가능한 아이디입니다."));
        }
    }

    /** 회원 가입 **/
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignupRequestDto req) {
        if(userService.register(req)){
            return ResponseEntity.ok(new ApiResponse<>(true, "회원가입이 완료되었습니다."));
        }
        return ResponseEntity.ok(new ApiResponse<>(false, "회원가입이 실패했습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequestDto req, HttpSession session) {
        // 1) 인증 로직
        Users user = userService.authenticate(req);

        // 2) 세션에 사용자 ID 저장
        session.setAttribute("loginUserId", user.getUserId());
        // (옵션) nickname을 세션에 보관해두면, 화면에서 사용자 이름 표시할 때 편리합니다.
        session.setAttribute("loginUserNickname", user.getNickname());

        // 스프링이 자동으로 Set-Cookie: JSESSIONID=… 헤더를 붙여 줍니다.
        return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공"));
    }

    /** 로그인 상태 확인용 엔드포인트 **/
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Object>> status(HttpSession session) {
        Long userId = (Long) session.getAttribute("loginUserId");
        String nickname = (String) session.getAttribute("loginUserNickname");

        if (userId != null && nickname != null) {
            // nickname도 함께 응답에 포함
            return ResponseEntity.ok(new ApiResponse<>(true, "로그인 상태",
                    Map.of("userId", userId, "nickname", nickname)
            ));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, "비로그인 상태"));
        }
    }

    /** 로그아웃 엔드포인트 **/
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        // 세션을 무효화(invalidate)하여 서버에 저장된 로그인 정보를 모두 삭제
        session.invalidate();
        return ResponseEntity.ok(new ApiResponse<>(true, "로그아웃 성공"));
    }
}
