package com.example.BE_14.controller;

import com.example.BE_14.service.SearchService;
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

}