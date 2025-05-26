package com.firstproject.auth.controller;
import com.firstproject.auth.dto.SignupForm;
import com.firstproject.auth.service.SignupService;
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
public class SignupController {
    private final SignupService signupService;

    @GetMapping("/signup")
    public String signup( Model model) {
        model.addAttribute("title", "SIGNUP");
        model.addAttribute("brand", "RUA");
        model.addAttribute("company", "본또보");
        model.addAttribute("error", "");
        return "auth/signup";
    }

    @PostMapping("/signuping")
    public String singuping(@Valid @ModelAttribute SignupForm signupForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "SIGNUP");
            model.addAttribute("brand", "RUA");
            model.addAttribute("company", "본또보");
            model.addAttribute("error","잘못된 접근입니다. 다시 입력해주세요.");
            return "auth/signup";
        }
        //서비스 로직 실행 (DTO 전달)
        if(signupService.processSignup(signupForm)){
            model.addAttribute("title", "LOGIN");
            model.addAttribute("brand", "RUA");
            model.addAttribute("company", "본또보");
            return "redirect:/login";
        }
        model.addAttribute("title", "SIGNUP");
        model.addAttribute("brand", "RUA");
        model.addAttribute("company", "본또보");
        model.addAttribute("error","중복된 ID입니다 다시 입력해주세요.");
        return "auth/signup";
    }
}
