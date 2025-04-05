package com.example.BE_14;

import com.example.BE_14.entity.Search;
import com.example.BE_14.entity.Score;
import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.repository.ScoreRepository;
import com.example.BE_14.repository.SearchRepository;
import com.example.BE_14.repository.StackRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final StackRepository stackRepository;
    private final SearchRepository searchRepository;
    private final ScoreRepository scoreRepository;

    public DataLoader(StackRepository stackRepository, SearchRepository searchRepository, ScoreRepository scoreRepository) {
        this.stackRepository = stackRepository;
        this.searchRepository = searchRepository;
        this.scoreRepository = scoreRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:/data/**/*.json");

        if (resources == null || resources.length == 0) {
            System.out.println("JSON 파일을 찾을 수 없습니다.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        int totalCount = 0;

        for (Resource resource : resources) {
            try (InputStream is = resource.getInputStream()) {
                JsonNode root = mapper.readTree(is);
                JsonNode stacksNode = root.path("stacks");
                if (stacksNode.isArray()) {
                    List<StackEntry> entries = new ArrayList<>();
                    List<Search> searches = new ArrayList<>();

                    for (JsonNode node : stacksNode) {
                        String department = node.path("department").asText();
                        String timestampStr = node.path("timestamp").asText();
                        LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);
                        String title = node.path("title").asText();
                        String url = node.path("url").asText();

                        StackEntry entry = StackEntry.builder()
                                .major(department)
                                .timestamp(timestamp)
                                .title(title)
                                .url(url)
                                .build();

                        JsonNode keywordsNode = node.path("keywords");
                        if (keywordsNode.isArray() && keywordsNode.size() >= 3) {
                            String depart = keywordsNode.get(0).asText();
                            LocalDate createdTime = LocalDate.parse(keywordsNode.get(1).asText());

                            for (int i = 2; i < keywordsNode.size(); i++) {
                                String keywordText = keywordsNode.get(i).asText();

                                // Search 저장
                                Search search = Search.builder()
                                        .keyword(keywordText)
                                        .depart(depart)
                                        .createdTime(createdTime)
                                        .stackEntry(entry)
                                        .build();
                                searches.add(search);

                                // Score 테이블 중복 확인 후 저장
                                scoreRepository.findByKeywordName(keywordText).orElseGet(() -> {
                                    Score newScore = Score.builder()
                                            .keywordName(keywordText)
                                            .score(0)
                                            .build();
                                    return scoreRepository.save(newScore);
                                });
                            }
                        }
                        entry.setSearches(searches);
                        entries.add(entry);
                    }
                    stackRepository.saveAll(entries);
                    System.out.println("파일 " + resource.getFilename() + " 처리 완료, " + entries.size() + " 건 저장됨.");
                    totalCount += entries.size();
                }
            } catch (Exception e) {
                System.out.println("파일 " + resource.getFilename() + " 처리 중 오류: " + e.getMessage());
            }
        }
        System.out.println("전체 저장 건수: " + totalCount);
    }
}
