package com.example.BE_14.controller;

import com.example.BE_14.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword) {
        return searchService.searchByKeyword(keyword);
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> recommendByUserKeywords(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("userId");
        return searchService.searchByUserPreference(userId);
    }



    // ✅ 2. 선호 키워드 기반 추천 (로그인된 유저 id 필요)
    @GetMapping("/preference/{userId}")
    public ResponseEntity<?> getUserPreferences(@PathVariable Long userId) {
        return searchService.searchByUserPreference(userId);
    }

    // ✅ 3. 프론트 클릭 시 점수 +5
    @PutMapping("/score")
    public ResponseEntity<?> addScore(@RequestParam("keyword") String keyword) {
        return searchService.increaseScoreByKeyword(keyword);
    }

    // ✅ 4. 인기 키워드 top 5
    @GetMapping("/top")
    public ResponseEntity<?> getTopKeywords() {
        return searchService.getTopKeywords();
    }
}