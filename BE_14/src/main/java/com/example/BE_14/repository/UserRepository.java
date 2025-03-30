package com.example.BE_14.repository;

import com.example.BE_14.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일을 통한 중복체크, 로그인 등에 사용
    Optional<User> findByEmail(String email);
}
