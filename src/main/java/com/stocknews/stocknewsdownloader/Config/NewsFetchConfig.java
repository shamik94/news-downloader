package com.stocknews.stocknewsdownloader.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@ConfigurationProperties(prefix = "news.fetch")
public class NewsFetchConfig {

    private String symbol;
    private String startDate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public LocalDate getParsedStartDate() {
        return LocalDate.parse(this.startDate);
    }

    public LocalDate getEndDate() {
        return LocalDate.now(); // Return the current date dynamically
    }
}
