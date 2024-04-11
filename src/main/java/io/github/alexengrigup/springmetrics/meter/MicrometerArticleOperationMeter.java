package io.github.alexengrigup.springmetrics.meter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.BaseUnits;
import org.springframework.stereotype.Component;

@Component
public class MicrometerArticleOperationMeter implements ArticleOperationMeter {
    private final Counter counterOfCreated;

    private final Counter counterOfReceived;
    private final Counter counterOfNotFoundForReceiving;

    private final Counter counterOfUpdated;
    private final Counter counterOfNotFoundForUpdating;

    private final Counter counterOfDeleted;
    private final Counter counterOfNotFoundForDeleting;

    public MicrometerArticleOperationMeter(MeterRegistry registry) {
        this.counterOfCreated = Counter.builder("spring-metrics.article.creating")
                .description("spring-metrics: number of created articles.")
                .baseUnit(BaseUnits.OBJECTS)
                .register(registry);

        String resultTagName = "result";
        String hitTag = "hit";
        String missTag = "miss";
        String receivingName = "spring-metrics.article.receiving";
        String receivingDescription = "spring-metrics: number of received articles" +
                "; where result is hit or miss if not found.";
        this.counterOfReceived = Counter.builder(receivingName)
                .description(receivingDescription)
                .baseUnit(BaseUnits.OBJECTS)
                .tags(resultTagName, hitTag)
                .register(registry);
        this.counterOfNotFoundForReceiving = Counter.builder(receivingName)
                .description(receivingDescription)
                .baseUnit(BaseUnits.OBJECTS)
                .tag(resultTagName, missTag)
                .register(registry);

        String updatingName = "spring-metrics.article.updating";
        String updatingDescription = "spring-metrics: number of updated articles" +
                "; where result is hit or miss if not found.";
        this.counterOfUpdated = Counter.builder(updatingName)
                .description(updatingDescription)
                .baseUnit(BaseUnits.OBJECTS)
                .tags(resultTagName, hitTag)
                .register(registry);
        this.counterOfNotFoundForUpdating = Counter.builder(updatingName)
                .description(updatingDescription)
                .baseUnit(BaseUnits.OBJECTS)
                .tags(resultTagName, missTag)
                .register(registry);

        String deletingName = "spring-metrics.article.deleting";
        String deletingDescription = "spring-metrics: number of deleted articles" +
                "; where result is hit or miss if not found.";
        this.counterOfDeleted = Counter.builder(deletingName)
                .description(deletingDescription)
                .baseUnit(BaseUnits.OBJECTS)
                .tags(resultTagName, hitTag)
                .register(registry);
        this.counterOfNotFoundForDeleting = Counter.builder(deletingName)
                .description(deletingDescription)
                .baseUnit(BaseUnits.OBJECTS)
                .tags(resultTagName, missTag)
                .register(registry);
    }

    @Override
    public void incrementCreated() {
        counterOfCreated.increment();
    }

    @Override
    public void incrementReceived() {
        counterOfReceived.increment();
    }

    @Override
    public void incrementNotFoundForReceiving() {
        counterOfNotFoundForReceiving.increment();
    }

    @Override
    public void incrementUpdated() {
        counterOfUpdated.increment();
    }

    @Override
    public void incrementNotFoundForUpdating() {
        counterOfNotFoundForUpdating.increment();
    }

    @Override
    public void incrementDeleted() {
        counterOfDeleted.increment();
    }

    @Override
    public void incrementNotFoundForDeleting() {
        counterOfNotFoundForDeleting.increment();
    }
}
