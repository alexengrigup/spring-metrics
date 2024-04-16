package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article create(CreatingArticle creatingArticle);

    List<Article> createAll(List<CreatingArticle> creatingArticles);

    Optional<Article> findById(String articleId);

    Optional<Article> updateById(String articleId, UpdatingArticle updatingArticle);

    boolean deleteById(String articleId);

    List<Article> findAllOn(LocalDate date);
}
