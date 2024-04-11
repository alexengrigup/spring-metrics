package io.github.alexengrigup.springmetrics.meter;

public interface ArticleOperationMeter {
    void incrementCreated();

    void incrementReceived();

    void incrementNotFoundForReceiving();

    void incrementUpdated();

    void incrementNotFoundForUpdating();

    void incrementDeleted();

    void incrementNotFoundForDeleting();
}
