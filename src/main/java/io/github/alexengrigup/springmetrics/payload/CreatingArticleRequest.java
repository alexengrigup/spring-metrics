package io.github.alexengrigup.springmetrics.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatingArticleRequest {
    private String body;
    private String title;
    private String author;
}
