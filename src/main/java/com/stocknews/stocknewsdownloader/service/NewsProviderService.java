package com.stocknews.stocknewsdownloader.service;


import com.stocknews.stocknewsdownloader.model.News;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface NewsProviderService {
    Flux<News> fetchNews(String symbol, LocalDate fromDate, LocalDate toDate);
}
