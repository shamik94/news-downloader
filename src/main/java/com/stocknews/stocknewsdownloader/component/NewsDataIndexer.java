package com.stocknews.stocknewsdownloader.component;

import com.stocknews.stocknewsdownloader.Config.NewsFetchConfig;
import com.stocknews.stocknewsdownloader.repository.NewsRepository;
import com.stocknews.stocknewsdownloader.service.NewsProviderService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class NewsDataIndexer {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsProviderService newsProviderService;

    @Autowired
    private NewsFetchConfig newsFetchConfig;

    @Scheduled(cron = "0 0 0 * * *")
    @PostConstruct
    public void fetchAndSaveNewsFromConfig() {
        fetchAndSaveNews(
                newsFetchConfig.getSymbol(),
                newsFetchConfig.getParsedStartDate(),
                newsFetchConfig.getEndDate()
        ).subscribe(
                unused -> System.out.println("Scheduled task completed successfully."),
                error -> System.err.println("Error during scheduled task: " + error.getMessage())
        );
    }


    public Mono<Void> fetchAndSaveNews(String symbol, LocalDate startDate, LocalDate endDate) {
        return newsProviderService.fetchNews(symbol, startDate, endDate) // Returns a Flux<News>
                .as(newsRepository::saveAll) // Save the Flux<News> directly
                .then() // Return a Mono<Void> after completion
                .doOnSuccess(unused -> System.out.println("News successfully fetched and saved to Elasticsearch for symbol: " + symbol))
                .doOnError(e -> {
                    System.err.println("Error fetching or saving data: " + e.getMessage());
                    e.printStackTrace();
                });
    }



}