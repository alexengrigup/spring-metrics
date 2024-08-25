package io.github.alexengrigup.springmetrics.meter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.BaseUnits;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class MicrometerDailyArticleCreatingMeter implements DailyArticleCreatingMeter {
    private final Timer timer;
    private final Counter counterOfEmpty;
    private final Counter counterOfNested;

    public MicrometerDailyArticleCreatingMeter(MeterRegistry registry) {
        this.timer = Timer.builder("spring-metrics.article.creating.daily")
                .description("spring-metrics: timer of creating daily articles.")
                .register(registry);
        this.counterOfEmpty = Counter.builder("spring-metrics.article.creating.daily.empty")
                .description("spring-metrics: number of daily articles not created without nested articles.")
                .baseUnit(BaseUnits.OBJECTS)
                .register(registry);
        this.counterOfNested = Counter.builder("spring-metrics.article.creating.daily.nested")
                .description("spring-metrics: number of nested articles to a daily article.")
                .baseUnit(BaseUnits.OBJECTS)
                .register(registry);
    }

    @Override
    public <T> T record(Supplier<T> creating) {
        return timer.record(creating);
    }

    @Override
    public void incrementEmpty() {
        counterOfEmpty.increment();
    }

    @Override
    public void incrementNested(int numberOfArticles) {
        counterOfNested.increment(numberOfArticles);
    }
}
