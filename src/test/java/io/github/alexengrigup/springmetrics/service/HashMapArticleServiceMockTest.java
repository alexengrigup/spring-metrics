package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;
import io.github.alexengrigup.springmetrics.generator.ArticleIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HashMapArticleService.class)
class HashMapArticleServiceMockTest {
    @Autowired
    ArticleService articleService;

    @MockBean
    ArticleIdGenerator articleIdGenerator;
    @MockBean
    Clock clock;

    String articleId = "Test id";
    LocalDate createdOn = LocalDate.of(2024, 2, 4);

    @BeforeEach
    void setUp() {
        ZoneId zone = ZoneId.systemDefault();
        Clock fixedClock = Clock.fixed(createdOn.atStartOfDay(zone).toInstant(), zone);
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        when(articleIdGenerator.generateId(any(Article.class))).thenReturn(articleId);
    }

    @Test
    void should_create_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        Article article = articleService.create(creatingArticle);
        Article exepctedArticle = Article.builder()
                .id(articleId)
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .createdOn(createdOn)
                .build();
        assertEquals(exepctedArticle, article);
    }

    @Test
    void should_return_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        articleService.create(creatingArticle);
        Optional<Article> optionalArticle = articleService.findById(articleId);
        assertTrue(optionalArticle.isPresent(), () -> "No article by id: " + articleId);
        Article article = optionalArticle.get();
        Article exepctedArticle = Article.builder()
                .id(articleId)
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .createdOn(createdOn)
                .build();
        assertEquals(exepctedArticle, article);
    }

    @Test
    void shouldNot_return_article() {
        String fakeArticleId = "Fake id";
        Optional<Article> optionalArticle = articleService.findById(fakeArticleId);
        assertTrue(optionalArticle.isEmpty(), () -> "Found article by id: " + fakeArticleId);
    }

    @Test
    void should_update_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        articleService.create(creatingArticle);
        UpdatingArticle updatingArticle = UpdatingArticle.builder()
                .body("Updated body.")
                .title("Updated title")
                .author("Updated author")
                .build();
        Optional<Article> optionalArticle = articleService.updateById(articleId, updatingArticle);
        assertTrue(optionalArticle.isPresent(), () -> "No article by id: " + articleId);
        Article article = optionalArticle.get();
        Article exepctedArticle = Article.builder()
                .id(articleId)
                .body("Updated body.")
                .title("Updated title")
                .author("Updated author")
                .createdOn(createdOn)
                .build();
        assertEquals(exepctedArticle, article);
    }

    @Test
    void shouldNot_update_article() {
        String fakeArticleId = "Fake id";
        UpdatingArticle updatingArticle = UpdatingArticle.builder()
                .body("Updated body.")
                .title("Updated title")
                .author("Updated author")
                .build();
        Optional<Article> optionalArticle = articleService.updateById(fakeArticleId, updatingArticle);
        assertTrue(optionalArticle.isEmpty(), () -> "Found article by id: " + fakeArticleId);
    }

    @Test
    void should_delete_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        articleService.create(creatingArticle);
        boolean deleted = articleService.deleteById(articleId);
        assertTrue(deleted, () -> "No article by id: " + articleId);
    }

    @Test
    void shouldNot_delete_article() {
        String fakeArticleId = "Fake id";
        boolean deleted = articleService.deleteById(fakeArticleId);
        assertFalse(deleted, () -> "Found article by id: " + fakeArticleId);
    }
}
