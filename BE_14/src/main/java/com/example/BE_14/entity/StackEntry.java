package com.example.BE_14.entity;

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

    private String major;

    private LocalDateTime timestamp;

    private String title;

    private String url;

    @OneToMany(mappedBy = "stackEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Search> searches = new ArrayList<>();
}