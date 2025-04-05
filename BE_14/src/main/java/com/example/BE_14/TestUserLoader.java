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
        createTestUser("test@example.com", "testpass", "김세모");
        createTestUser("test2@example.com", "testpass2", "이네모");
        createTestUser("test3@example.com", "testpass3", "박세모");
    }

    private void createTestUser(String email, String password, String name) {
        try {
            userService.login(email, password);
            System.out.println("테스트 계정 이미 존재합니다: " + email);
        } catch (IllegalArgumentException e) {
            SignUpRequest request = SignUpRequest.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .department("컴퓨터공학과")
                    .studyYear(1)
                    .transferMinor("none")
                    .build();
            userService.signUp(request);
            System.out.println("테스트 계정 생성 완료: " + email);
        }
    }
}
