package com.example.BE_14.service;

import com.example.BE_14.entity.Score;
import com.example.BE_14.entity.Search;
import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.repository.ScoreRepository;
import com.example.BE_14.repository.SearchRepository;
import com.example.BE_14.repository.StackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final SearchRepository searchRepository;
    private final ScoreRepository scoreRepository;
    private final StackRepository stackRepository;

    public SearchService(SearchRepository searchRepository,
                         ScoreRepository scoreRepository,
                         StackRepository stackRepository) {
        this.searchRepository = searchRepository;
        this.scoreRepository = scoreRepository;
        this.stackRepository = stackRepository;
    }

    public ResponseEntity<?> searchByKeyword(String keyword) {
        Optional<Score> scoreOpt = scoreRepository.findByKeywordName(keyword);

        if (scoreOpt.isPresent()) {
            // ✅ 키워드가 SCORE에 있는 경우: SEARCH에서 최신 20개
            List<Search> searches = searchRepository.findTop20ByKeywordOrderByCreatedTimeDesc(keyword);
            List<Map<String, Object>> results = searches.stream().map(search -> {
                StackEntry s = search.getStackEntry();
                Map<String, Object> map = new HashMap<>();
                map.put("id", s.getId());
                map.put("major", s.getMajor());
                map.put("timestamp", s.getTimestamp());
                map.put("title", s.getTitle());
                map.put("url", s.getUrl());
                map.put("keywords", s.getSearches().stream()
                        .map(Search::getKeyword)
                        .distinct()
                        .collect(Collectors.toList()));
                return map;
            }).collect(Collectors.toList());

            // SCORE 점수 증가
            Score score = scoreOpt.get();
            score.setScore(score.getScore() + 1);
            scoreRepository.save(score);

            return ResponseEntity.ok(results);

        } else {
            // ❌ 키워드가 SCORE에 없는 경우: StackEntry title에서 검색
            List<StackEntry> found = stackRepository.findTop5ByTitleContainingIgnoreCaseOrderByTimestampDesc(keyword);
            if (found.isEmpty()) {
                return ResponseEntity.ok("검색 결과가 없습니다.");
            }
            List<Map<String, Object>> results = found.stream().map(s -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", s.getId());
                map.put("major", s.getMajor());
                map.put("timestamp", s.getTimestamp());
                map.put("title", s.getTitle());
                map.put("url", s.getUrl());
                map.put("keywords", s.getSearches().stream()
                        .map(Search::getKeyword)
                        .distinct()
                        .collect(Collectors.toList()));
                return map;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(results);
        }
    }
}
