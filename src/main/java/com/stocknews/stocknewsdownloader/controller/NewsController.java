package com.stocknews.stocknewsdownloader.controller;

import com.stocknews.stocknewsdownloader.component.NewsDataIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("v1/api/news")
public class NewsController {

    @Autowired
    private NewsDataIndexer newsDataIndexer;

    @PostMapping("/fetch-and-save")
    public Mono<ResponseEntity<String>> fetchAndSaveNews(
            @RequestParam String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return newsDataIndexer.fetchAndSaveNews(symbol, startDate, endDate)
                .thenReturn(ResponseEntity.ok("News successfully fetched and saved to Elasticsearch for symbol: " + symbol))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error fetching or saving data: " + e.getMessage())));
    }

}