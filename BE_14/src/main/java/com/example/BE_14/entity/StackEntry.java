package com.example.BE_14.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "json_id")
    private String jsonId;

    private String major;

    private LocalDateTime timestamp;

    private String title;

    private String url;

    @OneToMany(mappedBy = "stackEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ JSON으로 보낼 때 이 필드는 무시
    private List<Search> searches = new ArrayList<>();

}