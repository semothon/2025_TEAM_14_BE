package com.example.BE_14.service;

import com.example.BE_14.entity.Search;
import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.entity.User;
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

    public SearchService(SearchRepository searchRepository, UserRepository userRepository) {
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
    }

    public List<Search> findAllByKeywordsSortedByCreatedTime(List<String> keywords) {
        return searchRepository.findByKeywordInOrderByCreatedTimeDesc(keywords)
                .stream()
                .filter(search -> search.getStackEntry().getUrl() != null)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                s -> s.getStackEntry().getUrl(),
                                s -> s,
                                (existing, replacement) -> existing, // 중복 URL 제거
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
        List<String> userKeywords = user.getUserKeywords();
        if (userKeywords == null || userKeywords.isEmpty() || (userKeywords.size() == 1 && userKeywords.contains("none"))) {
            return ResponseEntity.ok("등록된 선호 키워드가 없습니다.");
        }

        List<Search> results = findAllByKeywordsSortedByCreatedTime(userKeywords);
        return ResponseEntity.ok(results);
    }

}
