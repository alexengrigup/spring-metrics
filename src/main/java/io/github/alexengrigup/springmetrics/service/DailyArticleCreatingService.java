package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyArticleCreatingService {
    default void createDailyArticleOnNow() {
        createDailyArticleOn(LocalDate.now());
    }

    Optional<Article> createDailyArticleOn(LocalDate date);
}
