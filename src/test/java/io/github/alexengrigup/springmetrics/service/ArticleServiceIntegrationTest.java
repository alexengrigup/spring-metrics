package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ArticleServiceIntegrationTest {
    @Autowired
    ArticleService articleService;

    @Test
    void should_create_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Text.")
                .title("Header")
                .author("Writer")
                .build();
        Article article = articleService.create(creatingArticle);
        assertThat(article)
                .extracting(Article::getBody, Article::getTitle, Article::getAuthor)
                .containsExactly("Text.", "Header", "Writer");
    }

    @Test
    void should_return_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Text.")
                .title("Header")
                .author("Writer")
                .build();
        String articleId = articleService.create(creatingArticle).getId();
        Optional<Article> optionalArticle = articleService.findById(articleId);
        assertThat(optionalArticle)
                .isPresent()
                .get()
                .extracting(Article::getBody, Article::getTitle, Article::getAuthor)
                .containsExactly("Text.", "Header", "Writer");
    }

    @Test
    void should_update_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Text.")
                .title("Header")
                .author("Writer")
                .build();
        String articleId = articleService.create(creatingArticle).getId();
        UpdatingArticle updatingArticle = UpdatingArticle.builder()
                .body("Updated text.")
                .title("Updated header")
                .author("Updated writer")
                .build();
        Optional<Article> optionalArticle = articleService.updateById(articleId, updatingArticle);
        assertThat(optionalArticle)
                .isPresent()
                .get()
                .extracting(Article::getBody, Article::getTitle, Article::getAuthor)
                .containsExactly("Updated text.", "Updated header", "Updated writer");
    }

    @Test
    void should_delete_article() {
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .body("Text.")
                .title("Header")
                .author("Writer")
                .build();
        String articleId = articleService.create(creatingArticle).getId();
        boolean deleted = articleService.deleteById(articleId);
        assertTrue(deleted, "Not deleted");
        Optional<Article> optionalArticle = articleService.findById(articleId);
        assertTrue(optionalArticle.isEmpty(), () -> "Found article by id: " + articleId);
    }
}
