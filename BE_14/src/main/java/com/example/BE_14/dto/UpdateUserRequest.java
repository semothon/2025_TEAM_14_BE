package com.example.BE_14.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String name;
    private String department;
    private int studyYear;
    private String transferMinor;

    // 🔥 사용자 키워드 추가
    private List<String> userKeywords;
}
