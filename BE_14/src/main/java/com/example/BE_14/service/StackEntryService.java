package com.example.BE_14.service;

import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.repository.StackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StackEntryService {

    private final StackRepository stackRepository;

    public StackEntryService(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    // ID로 StackEntry 조회
    public Optional<StackEntry> findById(Long id) {
        return stackRepository.findById(id);
    }

    // 전체 StackEntry 조회
    public List<StackEntry> findAll() {
        return stackRepository.findAll();
    }

    // StackEntry 저장
    public StackEntry save(StackEntry entry) {
        return stackRepository.save(entry);
    }

    // 여러 개 저장
    public List<StackEntry> saveAll(List<StackEntry> entries) {
        return stackRepository.saveAll(entries);
    }

    // JSON ID로 조회 (중복 방지용)
    public List<StackEntry> findAllByJsonId(String jsonId) {
        return stackRepository.findAllByJsonId(jsonId);
    }


}
