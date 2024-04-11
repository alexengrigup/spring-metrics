package io.github.alexengrigup.springmetrics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.alexengrigup.springmetrics.converter.ArticleResponseConverter;
import io.github.alexengrigup.springmetrics.converter.CreatingArticleRequestConverter;
import io.github.alexengrigup.springmetrics.converter.UpdatingArticleRequestConverter;
import io.github.alexengrigup.springmetrics.domain.Article;
import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;
import io.github.alexengrigup.springmetrics.payload.ArticleResponse;
import io.github.alexengrigup.springmetrics.payload.CreatingArticleRequest;
import io.github.alexengrigup.springmetrics.payload.UpdatingArticleRequest;
import io.github.alexengrigup.springmetrics.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
        ArticleController.class,
        ArticleResponseConverter.class,
        CreatingArticleRequestConverter.class,
        UpdatingArticleRequestConverter.class
})
class ArticleControllerMockTest {
    static final String API_PREFIX = "/api/articles";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ArticleService articleService;

    @Test
    void should_create_article() throws Exception {
        Article article = Article.builder()
                .id("New id")
                .body("New body.")
                .title("New title")
                .author("New author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        when(articleService.create(any(CreatingArticle.class))).thenReturn(article);
        CreatingArticleRequest request = CreatingArticleRequest.builder()
                .body("New body.")
                .title("New title")
                .author("New author")
                .build();
        String responseBody = mvc.perform(post(API_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ArticleResponse response = objectMapper.readValue(responseBody, ArticleResponse.class);
        ArticleResponse expectedResponse = ArticleResponse.builder()
                .id("New id")
                .body("New body.")
                .title("New title")
                .author("New author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        assertEquals(expectedResponse, response);
    }

    @Test
    void should_return_article() throws Exception {
        String articleId = "Old id";
        Article article = Article.builder()
                .id(articleId)
                .body("Old body.")
                .title("Old title")
                .author("Old author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        when(articleService.findById(eq(articleId))).thenReturn(Optional.of(article));
        String responseBody = mvc.perform(get(API_PREFIX + "/{articleId}", articleId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ArticleResponse response = objectMapper.readValue(responseBody, ArticleResponse.class);
        ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(articleId)
                .body("Old body.")
                .title("Old title")
                .author("Old author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        assertEquals(expectedResponse, response);
    }

    @Test
    void shouldNot_return_article() throws Exception {
        String articleId = "Not found id";
        when(articleService.findById(eq(articleId))).thenReturn(Optional.empty());
        mvc.perform(get(API_PREFIX + "/{articleId}", articleId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update_article() throws Exception {
        String articleId = "Updated id";
        Article article = Article.builder()
                .id(articleId)
                .body("Updated body.")
                .title("Updated title")
                .author("Updated author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        UpdatingArticleRequest request = UpdatingArticleRequest.builder()
                .body("Updated body.")
                .title("Updated title")
                .author("Updated author")
                .build();
        when(articleService.updateById(eq(articleId), any(UpdatingArticle.class))).thenReturn(Optional.of(article));
        String responseBody = mvc.perform(put(API_PREFIX + "/{articleId}", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ArticleResponse response = objectMapper.readValue(responseBody, ArticleResponse.class);
        ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(articleId)
                .body("Updated body.")
                .title("Updated title")
                .author("Updated author")
                .createdOn(LocalDate.of(2024, 2, 4))
                .build();
        assertEquals(expectedResponse, response);
    }

    @Test
    void shouldNot_update_article() throws Exception {
        String articleId = "Not found id";
        UpdatingArticleRequest request = UpdatingArticleRequest.builder()
                .body("Updated body.")
                .title("Updated title")
                .author("Updated author")
                .build();
        when(articleService.updateById(eq(articleId), any(UpdatingArticle.class))).thenReturn(Optional.empty());
        mvc.perform(put(API_PREFIX + "/{articleId}", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_article() throws Exception {
        String articleId = "Deleted id";
        when(articleService.deleteById(eq(articleId))).thenReturn(true);
        mvc.perform(delete(API_PREFIX + "/{articleId}", articleId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNot_delete_article() throws Exception {
        String articleId = "Not found id";
        when(articleService.deleteById(eq(articleId))).thenReturn(false);
        mvc.perform(delete(API_PREFIX + "/{articleId}", articleId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}