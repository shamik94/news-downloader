package com.stocknews.stocknewsdownloader.service;

import com.stocknews.stocknewsdownloader.model.News;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class FinhubClientService implements NewsProviderService {

    private final WebClient webClient;
    private final URLBuilder urlBuilder;

    public FinhubClientService(WebClient.Builder webClientBuilder, URLBuilder urlBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://finnhub.io").build(); // Base URL for Finnhub API
        this.urlBuilder = urlBuilder;
    }

    @Override
    public Flux<News> fetchNews(String symbol, LocalDate fromDate, LocalDate toDate) {
        String url = urlBuilder.buildUrl(symbol, fromDate, toDate);

        return webClient.get()
                .uri(url) // Pass the dynamically built URL
                .retrieve() // Fetch the response
                .bodyToFlux(News.class); // Map the response body to Flux<News>
    }
}