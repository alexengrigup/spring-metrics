package io.github.alexengrigup.springmetrics.meter;

import java.util.function.Supplier;

public interface DailyArticleCreatingMeter {
    <T> T record(Supplier<T> creating);

    void incrementEmpty();

    void incrementNested(int numberOfArticles);
}
