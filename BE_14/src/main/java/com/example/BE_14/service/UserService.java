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
    // 비밀번호 암호화 등의 로직은 실제 구현 시 추가 권장

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User signUp(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        String transferMinor = (request.getTransferMinor() == null || request.getTransferMinor().trim().isEmpty())
                ? "none"
                : request.getTransferMinor();

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

    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일로 가입된 유저가 없습니다.");
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

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

        user.setStudyYear(request.getStudyYear());

        if (request.getTransferMinor() != null) {
            user.setTransferMinor(request.getTransferMinor());
        }

        // 🔥 키워드가 들어있으면 기존 키워드 덮어쓰기
        if (request.getUserKeywords() != null && !request.getUserKeywords().isEmpty()) {
            user.setUserKeywords(request.getUserKeywords());
        }

        return userRepository.save(user);
    }


    // 이메일 중복 체크 메서드 추가
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Get mapping 용
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        userRepository.delete(user);
    }


}
