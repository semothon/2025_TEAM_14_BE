package com.example.BE_14.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String department;
    private int studyYear;
    private String transferMinor;
}
