package com.stocknews.stocknewsdownloader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "news")
@JsonIgnoreProperties(ignoreUnknown = true)
public class News {

    @Id
    Long id;

    String headline;
    String image;
    String related;
    String source;
    String summary;
    String url;
    String category;
    String datetime;

    // TODO Add DateTime
}
