package com.firstproject.auth.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author rua
 */
@Controller
public class MainController {
    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        model.addAttribute("title", "MainPage");
        model.addAttribute("brand", "RUA");
        model.addAttribute("company", "본또보");

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        model.addAttribute("loggedIn", loggedIn != null && loggedIn);
        model.addAttribute("username", session.getAttribute("username"));
        return "main";
    }
}
