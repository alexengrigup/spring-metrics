package io.github.alexengrigup.springmetrics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @With
    private String id;
    private String body;
    private String title;
    private String author;
    private LocalDate createdOn;
}
