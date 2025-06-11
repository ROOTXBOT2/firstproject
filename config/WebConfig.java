package com.firstproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        // 프런트엔드가 띄워진 주소를 정확하게 적어 줌
                        .allowedOrigins("http://192.168.0.5:3000")
                        // 필요한 경우 더 추가 가능 (예: .allowedOrigins("http://localhost:3000", "http://192.168.0.5:3000"))
                        .allowedMethods("GET", "POST", "DELETE", "PATCH")
                        .allowCredentials(true);
            }
        };
    }
}