package io.github.alexengrigup.springmetrics.generator;

import io.github.alexengrigup.springmetrics.domain.Article;

public interface ArticleIdGenerator {
    String generateId(Article article);
}
