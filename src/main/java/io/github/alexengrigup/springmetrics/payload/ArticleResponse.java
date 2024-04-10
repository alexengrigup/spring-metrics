package io.github.alexengrigup.springmetrics.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private String id;
    private String body;
    private String title;
    private String author;
    private LocalDate createdOn;
}
