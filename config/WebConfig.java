//package com.firstproject.config;
//
//import com.firstproject.auth.service.AuthHandlerInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author rua
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthHandlerInterceptor())
//                .addPathPatterns("/**")              // 모든 요청에 대해 검사
//                .excludePathPatterns("/login", "/join", "/css/**", "/js/**", "/images/**"); // 로그인/회원가입/정적리소스 제외
//    }
//}
