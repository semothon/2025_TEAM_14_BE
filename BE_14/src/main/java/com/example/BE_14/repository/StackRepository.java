package com.example.BE_14.repository;

import com.example.BE_14.entity.StackEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StackRepository extends JpaRepository<StackEntry, Long> {
    List<StackEntry> findByTimestampBetweenOrderByTimestampDesc(LocalDateTime start, LocalDateTime end);
    List<StackEntry> findTop5ByTitleContainingIgnoreCaseOrderByTimestampDesc(String title);

}
