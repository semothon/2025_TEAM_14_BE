package com.example.BE_14.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stack_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StackEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // JSON 파일의 "id" 값을 저장 (필요에 따라 이름을 변경)
    @Column(name = "json_id")
    private String jsonId;

    private String major;

    private LocalDateTime timestamp;

    private String title;

    private String url;
}
