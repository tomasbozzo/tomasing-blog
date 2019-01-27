package com.tomasing.blog.service.mappers;

import com.tomasing.blog.repository.model.PostEntity;
import com.tomasing.blog.service.model.Post;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.apache.logging.log4j.Level.CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;

public class PostMapperTest {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String SLUG = "slug";
    private static final String CREATED_BY = "createdBy";
    private static final String PUBLISHED_BY = "publishedBy";
    private static final ZonedDateTime PUBLICATION_DATE = ZonedDateTime.now();
    private static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    private static final String UPDATED_BY = "updatedBy";
    private static final ZonedDateTime UPDATE_DATE = ZonedDateTime.now();

    @Test
    public void testToPost() {
        // Act
        Post result = PostMapper.toPost(createPostEntity());

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(createPost());
    }

    @Test
    public void testToPostNull() {
        // Act
        Post result = PostMapper.toPost(null);

        // Assert
        assertThat(result).isNull();
    }

    private Post createPost() {
        return Post.builder()
                .id(ID)
                .title(TITLE)
                .category(CATEGORY)
                .content(CONTENT)
                .slug(SLUG)
                .publishedBy(PUBLISHED_BY)
                .publicationDate(PUBLICATION_DATE)
                .build();
    }

    private PostEntity createPostEntity() {
        return PostEntity.builder()
                .id(ID)
                .title(TITLE)
                .category(CATEGORY)
                .content(CONTENT)
                .slug(SLUG)
                .publicationDate(PUBLICATION_DATE)
                .publishedBy(PUBLISHED_BY)
                .createDate(CREATE_DATE)
                .createdBy(CREATED_BY)
                .updateDate(UPDATE_DATE)
                .updatedBy(UPDATED_BY)
                .build();
    }
}