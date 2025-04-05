package com.example.BE_14.repository;

import com.example.BE_14.entity.Search;
import com.example.BE_14.entity.StackEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findTop20ByKeywordOrderByCreatedTimeDesc(String keyword);
    // 여러 키워드 기반 검색 (날짜 내림차순 정렬)
    List<Search> findByKeywordInOrderByCreatedTimeDesc(List<String> keywords);

    // 특정 StackEntry에 연결된 키워드 검색
    List<Search> findByStackEntry(StackEntry stackEntry);
}