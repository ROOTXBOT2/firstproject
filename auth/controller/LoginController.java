package com.firstproject.auth.controller;
import com.firstproject.auth.dto.LoginForm;
import com.firstproject.auth.service.loginService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author rua
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final loginService loginService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("title", "LOGIN");
        model.addAttribute("brand", "RUA");
        model.addAttribute("company", "본또보");
        model.addAttribute("error","");
        return "auth/login";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            // 잘못된 요청일 경우 반환할 로직
            model.addAttribute("title", "LOGIN");
            model.addAttribute("brand", "RUA");
            model.addAttribute("company", "본또보");
            model.addAttribute("error","잘못된 접근입니다. 다시 입력해주세요.");
            return "auth/login";
        }
        // (1) 로그인 검증 로직 + (2) 로그인 성공 실패시는 loginService 안에
        if(loginService.login(loginForm)) {
            // (2) 로그인 성공 시 세션에 로그인 정보 저장
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", loginForm.getId());
            return "redirect:/main";
        }else {
            model.addAttribute("title", "LOGIN");
            model.addAttribute("brand", "RUA");
            model.addAttribute("company", "본또보");
            model.addAttribute("error","아이디 or 비밀번호가 틀렸습니다.");
            return "auth/login";
        }
    }
}
