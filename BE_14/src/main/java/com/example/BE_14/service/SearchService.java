package com.example.BE_14.service;

import com.example.BE_14.entity.Score;
import com.example.BE_14.entity.Search;
import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.entity.User;
import com.example.BE_14.repository.ScoreRepository;
import com.example.BE_14.repository.SearchRepository;
import com.example.BE_14.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final SearchRepository searchRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    public SearchService(SearchRepository searchRepository, UserRepository userRepository, ScoreRepository scoreRepository) {
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
    }

    public List<Search> findAllByKeywordsSortedByCreatedTime(List<String> keywords) {
        return searchRepository.findByKeywordInOrderByCreatedTimeDesc(keywords)
                .stream()
                .filter(search -> search.getStackEntry().getUrl() != null)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                s -> s.getStackEntry().getUrl(),
                                s -> s,
                                (existing, replacement) -> existing,
                                LinkedHashMap::new),
                        m -> new ArrayList<>(m.values())
                ));
    }

    public List<String> getKeywordsByStackEntry(StackEntry entry) {
        return searchRepository.findByStackEntry(entry).stream()
                .map(Search::getKeyword)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> searchByKeyword(String keyword) {
        increaseKeywordScore(keyword, 1); // ✅ 검색 시 SCORE +1

        List<Search> result = findAllByKeywordsSortedByCreatedTime(List.of(keyword));
        if (result.isEmpty()) {
            return ResponseEntity.ok("검색 결과가 없습니다.");
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> searchByUserPreference(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("유저를 찾을 수 없습니다.");
        }

        User user = userOpt.get();
        List<String> keywords = user.getUserKeywords();
        if (keywords == null || keywords.contains("none")) {
            return ResponseEntity.ok("등록된 선호 키워드가 없습니다.");
        }

        List<Search> results = findAllByKeywordsSortedByCreatedTime(keywords);

        return ResponseEntity.ok(results);
    }

    // ✅ 프론트에서 클릭 시 점수 +5 API
    public ResponseEntity<?> increaseScoreByKeyword(String keyword) {
        increaseKeywordScore(keyword, 5);
        return ResponseEntity.ok("점수가 성공적으로 반영되었습니다.");
    }

    private void increaseKeywordScore(String keyword, int increment) {
        String normalizedKeyword = keyword.trim().toLowerCase(); // ✅ 공백 제거 및 소문자 통일
        Score score = scoreRepository.findByKeywordName(normalizedKeyword)
                .orElse(Score.builder().keywordName(normalizedKeyword).score(0).build());
        score.setScore(score.getScore() + increment);
        scoreRepository.save(score);
    }

    // ✅ 메인 화면 인기 키워드 top 5
    public ResponseEntity<?> getTopKeywords() {
        List<String> topKeywords = scoreRepository.findTop5ByOrderByScoreDesc()
                .stream()
                .map(Score::getKeywordName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topKeywords);
    }
}
