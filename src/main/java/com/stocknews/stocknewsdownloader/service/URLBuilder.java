package com.stocknews.stocknewsdownloader.service;

import com.stocknews.stocknewsdownloader.Config.FinnhubConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class URLBuilder {

    private final FinnhubConfig finnhubConfig;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public URLBuilder(FinnhubConfig finnhubConfig) {
        this.finnhubConfig = finnhubConfig;
    }

    public String buildUrl(String symbol, LocalDate fromDate, LocalDate toDate) {
        String fromDateString = fromDate.format(DATE_FORMATTER);
        String toDateString = toDate.format(DATE_FORMATTER);

        return String.format("%s?symbol=%s&from=%s&to=%s&token=%s",
                finnhubConfig.getUrl(), symbol, fromDateString, toDateString, finnhubConfig.getToken());
    }
}

