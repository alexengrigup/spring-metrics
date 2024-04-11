package io.github.alexengrigup.springmetrics.converter;

import io.github.alexengrigup.springmetrics.domain.UpdatingArticle;
import io.github.alexengrigup.springmetrics.payload.UpdatingArticleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UpdatingArticleRequestConverter.class)
class UpdatingArticleRequestConverterUnitTest {
    @Autowired
    Converter<UpdatingArticleRequest, UpdatingArticle> converter;

    @Test
    void should_convert() {
        UpdatingArticleRequest request = UpdatingArticleRequest.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        UpdatingArticle domain = converter.convert(request);
        UpdatingArticle expectedDomain = UpdatingArticle.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        assertEquals(expectedDomain, domain);
    }
}