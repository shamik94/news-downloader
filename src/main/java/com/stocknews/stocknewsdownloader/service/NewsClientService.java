package com.stocknews.stocknewsdownloader.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stocknews.stocknewsdownloader.model.News;

import java.util.List;

public interface NewsClientService {
    List<News> getNews() throws JsonProcessingException;
}

