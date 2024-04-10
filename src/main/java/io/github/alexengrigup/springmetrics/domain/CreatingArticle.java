package io.github.alexengrigup.springmetrics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatingArticle {
    private String body;
    private String title;
    private String author;
}
