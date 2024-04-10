package io.github.alexengrigup.springmetrics.generator;

import io.github.alexengrigup.springmetrics.domain.Article;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TitleAndCreatedOnArticleIdGenerator implements ArticleIdGenerator {
    private final DateTimeFormatter dateTimeFormatter;

    public TitleAndCreatedOnArticleIdGenerator() {
        this(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public TitleAndCreatedOnArticleIdGenerator(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public String generateId(Article article) {
        return article.getTitle().replace(' ', '-')
                + "-"
                + article.getCreatedOn().format(dateTimeFormatter);
    }
}
