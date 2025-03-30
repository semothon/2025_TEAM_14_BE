package com.example.BE_14.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // 테이블명
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PK
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;       // 로그인 ID 역할

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    // 예약어 충돌을 피하기 위해 studyYear로 변경
    // 실제 컬럼명은 study_year로 설정
    @Column(name = "study_year", nullable = false)
    private int studyYear;

    @Column(nullable = false)
    private String transferMinor; // 전과/부전공 여부
}
