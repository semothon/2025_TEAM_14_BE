package com.example.BE_14.service;

import com.example.BE_14.entity.Score;
import com.example.BE_14.entity.Search;
import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.entity.User;
import com.example.BE_14.repository.ScoreRepository;
import com.example.BE_14.repository.SearchRepository;
import com.example.BE_14.repository.StackRepository;
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
    private final StackRepository stackRepository;

    public SearchService(SearchRepository searchRepository, UserRepository userRepository, ScoreRepository scoreRepository, StackRepository stackRepository) {
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
        this.stackRepository = stackRepository;
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
        String normalizedKeyword = keyword.trim().toLowerCase();

        // ✅ 1. Score 테이블에 있으면 점수 +1, 없으면 무시
        scoreRepository.findByKeywordName(normalizedKeyword).ifPresent(score -> {
            score.setScore(score.getScore() + 1);
            scoreRepository.save(score);
        });

        // ✅ 2. Search 테이블에 keyword가 있으면 기존 방식대로 검색
        List<Search> searchMatches = searchRepository.findByKeywordInOrderByCreatedTimeDesc(List.of(keyword));
        if (!searchMatches.isEmpty()) {
            List<Search> filtered = searchMatches.stream()
                    .filter(s -> s.getStackEntry().getUrl() != null)
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    s -> s.getStackEntry().getUrl(),
                                    s -> s,
                                    (existing, replacement) -> existing,
                                    LinkedHashMap::new),
                            m -> new ArrayList<>(m.values())
                    ));
            return ResponseEntity.ok(filtered);
        }

        // ✅ 3. Search에 없고, StackEntry의 제목에 keyword 포함된 것들 최신순으로 가져오기
        List<StackEntry> stackEntries = stackRepository.findByTitleContainingIgnoreCaseOrderByTimestampDesc(keyword);
        if (stackEntries.isEmpty()) {
            return ResponseEntity.ok("검색 결과가 없습니다.");
        }

        // ✅ StackEntry -> Search 변환 (응답 형식 유지를 위해)
        List<Search> converted = stackEntries.stream()
                .map(entry -> Search.builder()
                        .id(-1L) // 가짜 ID (Search DB에는 없는 데이터임)
                        .keyword(keyword)
                        .createdTime(entry.getTimestamp().toLocalDate())
                        .depart(entry.getMajor())
                        .stackEntry(entry)
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(converted);
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