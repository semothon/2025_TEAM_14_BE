package com.example.BE_14.repository;

import com.example.BE_14.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByKeywordName(String keywordName);
    List<Score> findByKeywordNameIn(List<String> keywordNames); // 다수 조회
    List<Score> findTop5ByOrderByScoreDesc();
}
