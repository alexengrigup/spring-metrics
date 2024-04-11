package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;
import io.github.alexengrigup.springmetrics.generator.ArticleIdGenerator;
import io.github.alexengrigup.springmetrics.meter.ArticleOperationMeter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashMapArticleService implements ArticleService {
    private final Map<String, Article> articleById = new HashMap<>();

    private final ArticleIdGenerator articleIdGenerator;
    private final ArticleOperationMeter articleOperationMeter;
    private final Clock clock;

    @Override
    public Article create(CreatingArticle creatingArticle) {
        Article newArticle = Article.builder()
                .body(creatingArticle.getBody())
                .title(creatingArticle.getTitle())
                .author(creatingArticle.getAuthor())
                .createdOn(LocalDate.now(clock))
                .build();
        String id = articleIdGenerator.generateId(newArticle);
        Article article = newArticle.withId(id);
        articleById.put(id, article);
        log.debug("Created article '{}' by '{}' with id: {}",
                article.getTitle(), article.getAuthor(), article.getId());
        articleOperationMeter.incrementCreated();
        return article;
    }

    @Override
    public Optional<Article> findById(String articleId) {
        Article article = articleById.get(articleId);
        if (article == null) {
            log.debug("Not found article with id: {}", articleId);
            articleOperationMeter.incrementNotFoundForReceiving();
            return Optional.empty();
        }
        log.debug("Found article with id: {}", articleId);
        articleOperationMeter.incrementReceived();
        return Optional.of(article);
    }

    @Override
    public Optional<Article> updateById(String articleId, UpdatingArticle updatingArticle) {
        Article article = articleById.get(articleId);
        if (article == null) {
            log.debug("Not found article for updating with id: {}", articleId);
            articleOperationMeter.incrementNotFoundForUpdating();
            return Optional.empty();
        }
        PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        mapper.from(updatingArticle.getBody()).to(article::setBody);
        mapper.from(updatingArticle.getTitle()).to(article::setTitle);
        mapper.from(updatingArticle.getAuthor()).to(article::setAuthor);
        articleById.put(article.getId(), article);
        log.debug("Updated article '{}' by '{}' with id: {}",
                article.getTitle(), article.getAuthor(), article.getId());
        articleOperationMeter.incrementUpdated();
        return Optional.of(article);
    }

    @Override
    public boolean deleteById(String articleId) {
        Article article = articleById.remove(articleId);
        if (article == null) {
            log.debug("Not found article for deleting with id: {}", articleId);
            articleOperationMeter.incrementNotFoundForDeleting();
            return false;
        }
        log.debug("Deleted article '{}' by '{}' with id: {}",
                article.getTitle(), article.getAuthor(), article.getId());
        articleOperationMeter.incrementDeleted();
        return true;
    }
}
