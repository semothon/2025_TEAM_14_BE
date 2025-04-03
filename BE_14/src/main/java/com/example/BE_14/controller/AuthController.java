package com.example.BE_14.controller;

import com.example.BE_14.dto.LoginRequest;
import com.example.BE_14.dto.SignUpRequest;
import com.example.BE_14.entity.User;
import com.example.BE_14.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.BE_14.dto.UserResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            User newUser = userService.signUp(signUpRequest);
            return ResponseEntity.ok("회원가입 성공! 유저 ID: " + newUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("오류 발생: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            return ResponseEntity.ok("로그인 성공! userId=" + user.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("로그인 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("오류 발생: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃되었습니다.");
    }

    // 이메일 중복 체크 API (GET 요청, 쿼리 파라미터 이용)
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        if (userService.emailExists(email)) {
            return ResponseEntity.ok("이미 사용 중인 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("userId");
        try {
            User user = userService.getUserById(userId);
            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .department(user.getDepartment())
                    .studyYear(user.getStudyYear())
                    .transferMinor(user.getTransferMinor())
                    .build();
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유저 조회 실패: " + e.getMessage());
        }
    }
}
