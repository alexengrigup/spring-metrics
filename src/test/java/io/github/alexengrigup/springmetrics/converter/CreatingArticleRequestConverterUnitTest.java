package io.github.alexengrigup.springmetrics.converter;

import io.github.alexengrigup.springmetrics.domain.CreatingArticle;
import io.github.alexengrigup.springmetrics.payload.CreatingArticleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CreatingArticleRequestConverter.class)
class CreatingArticleRequestConverterUnitTest {
    @Autowired
    Converter<CreatingArticleRequest, CreatingArticle> converter;

    @Test
    void should_convert() {
        CreatingArticleRequest request = CreatingArticleRequest.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        CreatingArticle domain = converter.convert(request);
        CreatingArticle expectedDomain = CreatingArticle.builder()
                .body("Test body.")
                .title("Test title")
                .author("Test author")
                .build();
        assertEquals(expectedDomain, domain);
    }
}