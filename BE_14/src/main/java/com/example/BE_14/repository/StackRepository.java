package com.example.BE_14.repository;

import com.example.BE_14.entity.StackEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StackRepository extends JpaRepository<StackEntry, Long> {
    Optional<StackEntry> findByJsonId(String jsonId);
}
