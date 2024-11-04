package com.stocknews.stocknewsdownloader.component;

import com.stocknews.stocknewsdownloader.repository.NewsRepository;
import com.stocknews.stocknewsdownloader.service.NewsClientService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NewsDataIndexer {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    NewsClientService newsClientService;

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    @PostConstruct
    public void fetchAndSaveNews() {
        try {
            // Save data to Elasticsearch
            newsRepository.saveAll(newsClientService.getNews()).collectList().block();
            System.out.println("Data successfully fetched and saved to Elasticsearch");
        } catch (Exception e) {
            System.err.println("Error fetching or saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
