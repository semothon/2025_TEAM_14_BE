package com.example.BE_14.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @Column(name = "study_year", nullable = false)
    private int studyYear;

    @Column
    private String transferMinor;

    // ✅ 사용자 키워드 목록 - 초기 none 포함
    @ElementCollection
    @CollectionTable(name = "user_keywords", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "keyword")
    @Builder.Default
    private List<String> userKeywords = new ArrayList<>(List.of("none"));
}