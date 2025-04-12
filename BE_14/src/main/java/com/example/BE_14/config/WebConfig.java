package com.example.BE_14.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// ngrok 설정, 프론트와 웹으로 연결, 무료, 프론트 서버 실행시 매번 사이트 바뀜

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://51fd-61-98-214-250.ngrok-free.app") // React dev server, 프론트 서버 사이트랑 연동
                .allowedMethods("*")
                .allowCredentials(true);
    }
}