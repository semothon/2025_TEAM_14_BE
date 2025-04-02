package com.example.BE_14.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String name;
    private String department;
    // "year" -> "studyYear" 로 변경
    private int studyYear;
    private String transferMinor;
}
