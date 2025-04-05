package com.example.BE_14.repository;

import com.example.BE_14.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findTop20ByKeywordOrderByCreatedTimeDesc(String keyword);

}