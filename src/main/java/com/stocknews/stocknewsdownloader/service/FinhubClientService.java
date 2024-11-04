package com.stocknews.stocknewsdownloader.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocknews.stocknewsdownloader.model.News;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class FinhubClientService implements NewsClientService{


    @Value("${finnhub.api.token}")
    private String apiToken;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public FinhubClientService() {
        this.webClient = WebClient.builder().baseUrl("https://finnhub.io").build();
        this.objectMapper = new ObjectMapper();
    }

    public List<News> getNews() throws JsonProcessingException {
        // Calculate dates
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        String fromDate = yesterday.format(DateTimeFormatter.ISO_DATE);
        String toDate = today.format(DateTimeFormatter.ISO_DATE);

        // TODO read from stocks in resources and fetch data for all stocks

        // Build the URL with dynamic dates
        String url = String.format("/api/v1/company-news?symbol=MSFT&from=%s&to=%s&token=%s",
                fromDate, toDate, apiToken);

        // Fetch data from the API
        String response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Parse JSON response to List<News> and return it
        return objectMapper.readValue(response, new TypeReference<List<News>>() {});
    }
}
