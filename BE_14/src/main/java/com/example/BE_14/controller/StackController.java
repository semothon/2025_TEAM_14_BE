package com.example.BE_14.controller;

import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.repository.StackRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stack")
public class StackController {

    private final StackRepository stackRepository;

    public StackController(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    @GetMapping("/2025/03")
    public List<StackEntry> getMarch2025Stacks() {
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 31, 23, 59, 59);
        return stackRepository.findByTimestampBetweenOrderByTimestampDesc(start, end);
    }
}
