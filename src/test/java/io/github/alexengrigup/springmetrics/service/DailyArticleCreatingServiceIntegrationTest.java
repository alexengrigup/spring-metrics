package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class DailyArticleCreatingServiceIntegrationTest {
    @Autowired
    DailyArticleCreatingService dailyArticleCreatingService;
    @Autowired
    ArticleService articleService;

    @Test
    void should_create_dailyArticle() {
        List<Article> articles = articleService.createAll(List.of(
                CreatingArticle.builder()
                        .body("First body.")
                        .title("First title")
                        .author("First author")
                        .build(),
                CreatingArticle.builder()
                        .body("Second body.")
                        .title("Second title")
                        .author("Second author")
                        .build()
        ));
        LocalDate date = articles.get(0).getCreatedOn();
        Optional<Article> optionalDailyArticle = dailyArticleCreatingService.createDailyArticleOn(date);
        assertThat(optionalDailyArticle)
                .isPresent()
                .get()
                .extracting(Article::getBody, Article::getTitle, Article::getAuthor)
                .containsExactly(
                        """
                                Hello there!
                                Today we have the following articles:
                                 - "First title" by First author.
                                 - "Second title" by Second author.""",
                        "Articles on " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        "Daily-bot"
                );
    }
}