package com.example.BE_14;

import com.example.BE_14.entity.Keyword;
import com.example.BE_14.entity.StackEntry;
import com.example.BE_14.repository.StackRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final StackRepository stackRepository;

    public DataLoader(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
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

// 키워드 처리
                        JsonNode keywordsNode = node.path("keywords");
                        if (keywordsNode.isArray()) {
                            List<Keyword> keywordEntities = new ArrayList<>();
                            for (JsonNode keywordNode : keywordsNode) {
                                Keyword keyword = Keyword.builder()
                                        .keyword(keywordNode.asText())
                                        .stackEntry(entry) // 연관 설정
                                        .build();
                                keywordEntities.add(keyword);
                            }
                            entry.setKeywords(keywordEntities);
                        }

                        entries.add(entry);
                    }

                    if (!entries.isEmpty()) {
                        stackRepository.saveAll(entries);
                        totalCount += entries.size();
                        System.out.println("파일 " + resource.getFilename() + " 처리 완료, " + entries.size() + " 건 저장됨.");
                    } else {
                        System.out.println("파일 " + resource.getFilename() + " 에서 저장할 데이터 없음.");
                    }
                }
            } catch (Exception e) {
                System.out.println("파일 " + resource.getFilename() + " 처리 중 오류: " + e.getMessage());
            }
        }

        System.out.println("전체 저장 건수: " + totalCount);
    }
}
