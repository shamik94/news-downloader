package com.stocknews.stocknewsdownloader.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocknews.stocknewsdownloader.model.News;
import com.stocknews.stocknewsdownloader.repository.NewsRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class DataDownloader {

    @Autowired
    private NewsRepository newsRepository;

    @Value("${finnhub.api.token}")
    private String apiToken;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public DataDownloader() {
        this.webClient = WebClient.builder().baseUrl("https://finnhub.io").build();
        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    @PostConstruct
    public void fetchAndSaveNews() {
        try {
            // Calculate dates
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

            String fromDate = yesterday.format(DateTimeFormatter.ISO_DATE);
            String toDate = today.format(DateTimeFormatter.ISO_DATE);

            // Build the URL with dynamic dates
            String url = String.format("/api/v1/company-news?symbol=MSFT&from=%s&to=%s&token=%s",
                    fromDate, toDate, apiToken);

            // Fetch data from the API
            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse JSON response to List<News>
            List<News> newsList = objectMapper.readValue(response, new TypeReference<List<News>>() {});

            // Save data to Elasticsearch
            newsRepository.saveAll(newsList).collectList().block();

            System.out.println("Data successfully fetched and saved to Elasticsearch");
        } catch (Exception e) {
            System.err.println("Error fetching or saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
