package com.stocknews.stocknewsdownloader.repository;

import com.stocknews.stocknewsdownloader.model.News;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends ReactiveElasticsearchRepository<News, Long> {
}
