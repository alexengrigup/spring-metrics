package io.github.alexengrigup.springmetrics.converter;

import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.payload.CreatingArticleRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreatingArticleRequestConverter implements Converter<CreatingArticleRequest, CreatingArticle> {
    @Override
    public CreatingArticle convert(CreatingArticleRequest request) {
        return CreatingArticle.builder()
                .body(request.getBody())
                .title(request.getTitle())
                .author(request.getAuthor())
                .build();
    }
}
