package com.example.BE_14.controller;

import com.example.BE_14.dto.UpdateUserRequest;
import com.example.BE_14.entity.User;
import com.example.BE_14.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateRequest, HttpServletRequest request) {
        // 세션에서 로그인된 userId를 꺼내옴
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("userId");

        try {
            User updatedUser = userService.updateUser(userId, updateRequest);
            return ResponseEntity.ok("회원정보 수정 완료! userId=" + updatedUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("회원정보 수정 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("오류 발생: " + e.getMessage());
        }
    }

    // ✅ 회원탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("userId");

        try {
            userService.deleteUser(userId);
            session.invalidate(); // 로그아웃
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("회원 탈퇴 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("오류 발생: " + e.getMessage());
        }
    }
}
