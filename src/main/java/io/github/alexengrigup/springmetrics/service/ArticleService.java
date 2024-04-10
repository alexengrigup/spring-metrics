package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;

import java.util.Optional;

public interface ArticleService {
    Article create(CreatingArticle creatingArticle);

    Optional<Article> findById(String articleId);

    Optional<Article> updateById(String articleId, UpdatingArticle updatingArticle);

    boolean deleteById(String articleId);
}
