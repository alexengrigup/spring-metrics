package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = SimpleDailyArticleCreatingService.class)
class SimpleDailyArticleCreatingServiceMockTest {
    @Autowired
    DailyArticleCreatingService dailyArticleCreatingService;

    @MockBean
    ArticleService articleService;

    @Test
    void should_create_dailyArticle() {
        LocalDate date = LocalDate.of(2024, 2, 4);
        when(articleService.findAllOn(eq(date))).thenReturn(List.of(
                Article.builder()
                        .id("First id")
                        .body("First body.")
                        .title("First title")
                        .author("First author")
                        .createdOn(date)
                        .build(),
                Article.builder()
                        .id("Second id")
                        .body("Second body.")
                        .title("Second title")
                        .author("Second author")
                        .createdOn(date)
                        .build()
        ));
        when(articleService.create(any(CreatingArticle.class))).thenReturn(Article.builder().build());
        Optional<Article> optionalDailyArticle = dailyArticleCreatingService.createDailyArticleOn(date);
        assertTrue(optionalDailyArticle.isPresent());
        verify(articleService).create(eq(CreatingArticle.builder()
                .author("Daily-bot")
                .title("Articles on 04.02.2024")
                .body("""
                        Hello there!
                        Today we have the following articles:
                         - "First title" by First author.
                         - "Second title" by Second author.""")
                .build()));
    }
}