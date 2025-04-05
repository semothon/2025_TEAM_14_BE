package com.example.BE_14.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scores", uniqueConstraints = @UniqueConstraint(columnNames = "keywordName"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keywordName;

    private int score = 0; // 생성 시 기본값으로 0 설정
}