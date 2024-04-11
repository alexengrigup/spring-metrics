package io.github.alexengrigup.springmetrics.generator;

import io.github.alexengrigup.springmetrics.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TitleAndCreatedOnArticleIdGenerator.class)
class TitleAndCreatedOnArticleIdGeneratorUnitTest {
    @Autowired
    ArticleIdGenerator idGenerator;

    @Test
    void should_generate_article_id() {
        Article article = Article.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        String id = idGenerator.generateId(article);
        assertEquals("Test-title-2024-02-04", id);
    }
}