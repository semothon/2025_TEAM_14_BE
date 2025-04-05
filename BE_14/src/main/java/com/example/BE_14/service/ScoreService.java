package com.example.BE_14.service;

import com.example.BE_14.entity.Score;
import com.example.BE_14.entity.Search;
import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.repository.ScoreRepository;
import com.example.BE_14.repository.SearchRepository;
import com.example.BE_14.repository.StackRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final StackRepository stackRepository;
    private final SearchRepository searchRepository;

    public ScoreService(ScoreRepository scoreRepository, StackRepository stackRepository, SearchRepository searchRepository) {
        this.scoreRepository = scoreRepository;
        this.stackRepository = stackRepository;
        this.searchRepository = searchRepository;
    }

    public void addScore(String keyword, int amount) {
        Optional<Score> scoreOpt = scoreRepository.findByKeywordName(keyword);
        if (scoreOpt.isPresent()) {
            Score score = scoreOpt.get();
            score.setScore(score.getScore() + amount);
            scoreRepository.save(score);
        } else {
            Score newScore = Score.builder()
                    .keywordName(keyword)
                    .score(amount)
                    .build();
            scoreRepository.save(newScore);
        }
    }

    public List<StackEntry> getFilteredRecentStackEntriesByKeywords(List<String> keywords) {
        // 중복 URL 제거를 위해 Set 사용
        Set<String> seenUrls = new HashSet<>();
        List<Search> allSearches = searchRepository.findByKeywordInOrderByCreatedTimeDesc(keywords);

        Map<Long, StackEntry> uniqueEntries = new LinkedHashMap<>();
        for (Search search : allSearches) {
            StackEntry entry = search.getStackEntry();
            if (!seenUrls.contains(entry.getUrl())) {
                uniqueEntries.put(entry.getId(), entry);
                seenUrls.add(entry.getUrl());
            }
        }

        // 최신 날짜 기준 정렬하여 반환
        return uniqueEntries.values().stream()
                .sorted(Comparator.comparing(StackEntry::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
}
