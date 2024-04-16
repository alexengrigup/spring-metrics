package io.github.alexengrigup.springmetrics.scheduler;

import io.github.alexengrigup.springmetrics.service.DailyArticleCreatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyArticleCreatingScheduler {
    private final DailyArticleCreatingService dailyArticleCreatingService;

    @Scheduled(cron = "${scheduler.article.creating.daily.cron:0 0 18 * * ?}")
    public void scheduleCreatingDailyArticle() {
        dailyArticleCreatingService.createDailyArticleOnNow();
    }
}
