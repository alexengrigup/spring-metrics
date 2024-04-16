package io.github.alexengrigup.springmetrics.service;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleDailyArticleCreatingService implements DailyArticleCreatingService {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ArticleService articleService;

    @Override
    public Optional<Article> createDailyArticleOn(LocalDate date) {
        String stringDate = dateFormatter.format(date);
        List<Article> articlesOnDay = articleService.findAllOn(date);
        if (articlesOnDay.isEmpty()) {
            log.debug("No articles on {} for creating a daily article", stringDate);
            return Optional.empty();
        }
        String articles = articlesOnDay.stream()
                .map(article -> " - \"" + article.getTitle() + "\" by " + article.getAuthor() + ".")
                .collect(Collectors.joining("\n"));
        CreatingArticle creatingArticle = CreatingArticle.builder()
                .author("Daily-bot")
                .title("Articles on " + stringDate)
                .body("Hello there!\nToday we have the following articles:\n" + articles)
                .build();
        Article article = articleService.create(creatingArticle);
        log.debug("Created a daily article on {} with {} article(-s) and id: {}",
                stringDate, articlesOnDay.size(), article.getId());
        return Optional.of(article);
    }
}
