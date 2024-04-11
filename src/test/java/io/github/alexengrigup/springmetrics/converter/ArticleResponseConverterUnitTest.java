package io.github.alexengrigup.springmetrics.converter;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.payload.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ArticleResponseConverter.class)
class ArticleResponseConverterUnitTest {
    @Autowired
    Converter<Article, ArticleResponse> converter;

    @Test
    void should_convert() {
        Article domain = Article.builder()
                .id("Test id")
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        ArticleResponse response = converter.convert(domain);
        ArticleResponse expectedResponse = ArticleResponse.builder()
                .id("Test id")
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        assertEquals(expectedResponse, response);
    }
}