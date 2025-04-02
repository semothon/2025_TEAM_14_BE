package com.example.BE_14;

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
        // classpath 아래의 data 폴더 내 모든 하위 폴더의 *.json 파일 검색
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
                        String jsonId = node.path("id").asText();
                        String major = node.path("major").asText();
                        String timestampStr = node.path("timestamp").asText();
                        LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);
                        String title = node.path("title").asText();
                        String url = node.path("url").asText();

                        StackEntry entry = StackEntry.builder()
                                .jsonId(jsonId)
                                .major(major)
                                .timestamp(timestamp)
                                .title(title)
                                .url(url)
                                .build();
                        entries.add(entry);
                    }
                    stackRepository.saveAll(entries);
                    totalCount += entries.size();
                    System.out.println("파일 " + resource.getFilename() + " 처리 완료, " + entries.size() + " 건 저장됨.");
                }
            } catch (Exception e) {
                System.out.println("파일 " + resource.getFilename() + " 처리 중 오류: " + e.getMessage());
            }
        }
        System.out.println("전체 저장 건수: " + totalCount);
    }
}
