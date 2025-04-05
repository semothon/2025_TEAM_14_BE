package com.example.BE_14.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "searches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    private LocalDate createdTime;

    private String depart;

    @Getter
    @ManyToOne
    @JoinColumn(name = "stack_entry_id")
    private StackEntry stackEntry;

}
