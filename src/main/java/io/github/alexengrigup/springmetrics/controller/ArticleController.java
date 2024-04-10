package io.github.alexengrigup.springmetrics.controller;

import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;
import io.github.alexengrigup.springmetrics.payload.ArticleResponse;
import io.github.alexengrigup.springmetrics.payload.CreatingArticleRequest;
import io.github.alexengrigup.springmetrics.payload.UpdatingArticleRequest;
import io.github.alexengrigup.springmetrics.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    private final Converter<CreatingArticleRequest, CreatingArticle> creatingArticleRequestConverter;
    private final Converter<UpdatingArticleRequest, UpdatingArticle> updatingArticleRequestConverter;
    private final Converter<Article, ArticleResponse> articleResponseConverter;

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(
            @RequestBody CreatingArticleRequest request
    ) {
        CreatingArticle creatingArticle = creatingArticleRequestConverter.convert(request);
        Article article = articleService.create(creatingArticle);
        return ResponseEntity.ok(articleResponseConverter.convert(article));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(
            @PathVariable String articleId
    ) {
        return articleService.findById(articleId)
                .map(articleResponseConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable String articleId,
            @RequestBody UpdatingArticleRequest request
    ) {
        UpdatingArticle updatingArticle = updatingArticleRequestConverter.convert(request);
        return articleService.updateById(articleId, updatingArticle)
                .map(articleResponseConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> deleteArticle(
            @PathVariable String articleId
    ) {
        if (articleService.deleteById(articleId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
