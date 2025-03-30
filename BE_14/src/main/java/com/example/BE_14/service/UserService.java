package com.example.BE_14.service;

import com.example.BE_14.dto.SignUpRequest;
import com.example.BE_14.dto.UpdateUserRequest;
import com.example.BE_14.entity.User;
import com.example.BE_14.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    // 실제로는 비밀번호 암호화를 위해 PasswordEncoder 사용 권장

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public User signUp(SignUpRequest request) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 전과/부전공 여부가 없다면 "none"
        String transferMinor = (request.getTransferMinor() == null
                || request.getTransferMinor().trim().isEmpty())
                ? "none"
                : request.getTransferMinor();

        // 비밀번호 암호화 필요 (예: passwordEncoder.encode(...))
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .department(request.getDepartment())
                .studyYear(request.getStudyYear())
                .transferMinor(transferMinor)
                .build();

        return userRepository.save(user);
    }

    // 로그인
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일로 가입된 유저가 없습니다.");
        }
        User user = userOpt.get();
        // 실제로는 passwordEncoder.matches(...) 로 비교
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    // 회원정보 수정
    @Transactional
    public User updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getDepartment() != null) {
            user.setDepartment(request.getDepartment());
        }
        // 변경: studyYear 반영
        user.setStudyYear(request.getStudyYear());

        if (request.getTransferMinor() != null) {
            user.setTransferMinor(request.getTransferMinor());
        }

        return userRepository.save(user);
    }
}
