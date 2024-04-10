package io.github.alexengrigup.springmetrics.converter;

import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;
import io.github.alexengrigup.springmetrics.payload.UpdatingArticleRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdatingArticleRequestConverter implements Converter<UpdatingArticleRequest, UpdatingArticle> {
    @Override
    public UpdatingArticle convert(UpdatingArticleRequest request) {
        return UpdatingArticle.builder()
                .body(request.getBody())
                .title(request.getTitle())
                .author(request.getAuthor())
                .build();
    }
}
