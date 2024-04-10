package io.github.alexengrigup.springmetrics.converter;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.payload.ArticleResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleResponseConverter implements Converter<Article, ArticleResponse> {
    @Override
    public ArticleResponse convert(Article domain) {
        return ArticleResponse.builder()
                .id(domain.getId())
                .body(domain.getBody())
                .title(domain.getTitle())
                .author(domain.getAuthor())
                .createdOn(domain.getCreatedOn())
                .build();
    }
}
