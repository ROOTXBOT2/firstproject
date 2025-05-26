//package com.firstproject.auth.service;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.web.servlet.HandlerInterceptor;
//
///**
// * @author rua
// */
//public class AuthHandlerInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
//
//        if (loggedIn == null || !loggedIn) {
//            response.sendRedirect("/login");  // 로그인 페이지로 리디렉트
//            return false; // Controller 접근 차단
//        }
//
//        return true; // 로그인 상태면 계속 진행
//    }
//
//}
