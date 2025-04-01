package com.example.BE_14;

import com.example.BE_14.dto.SignUpRequest;
import com.example.BE_14.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestUserLoader implements CommandLineRunner {

    private final UserService userService;

    public TestUserLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String testEmail = "test@example.com";
        try {
            // 만약 이미 로그인 가능한 계정이면 예외 발생하지 않고 기존 계정이 있음
            userService.login(testEmail, "testpass");
            System.out.println("테스트 계정 이미 존재합니다: " + testEmail);
        } catch (IllegalArgumentException e) {
            // 계정이 없으면 새로 생성
            SignUpRequest request = SignUpRequest.builder()
                    .email(testEmail)
                    .password("testpass")
                    .name("Test User")
                    .department("Test Department")
                    .studyYear(1)
                    .transferMinor("none")
                    .build();
            userService.signUp(request);
            System.out.println("테스트 계정 생성 완료: " + testEmail);
        }
    }
}
