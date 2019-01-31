package com.tomasing.blog.repository.mappers;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.tomasing.blog.repository.model.PostEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.apache.logging.log4j.Level.CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostEntityMapperTest {

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

    @Mock
    private QueryDocumentSnapshot document;

    @Test
    public void testToPostEntity() {
        // Arrange
        when(document.getId()).thenReturn(ID);
        when(document.getString("title")).thenReturn(TITLE);
        when(document.getString("content")).thenReturn(CONTENT);
        when(document.getString("slug")).thenReturn(SLUG);
        when(document.getString("category")).thenReturn(CATEGORY);
        when(document.getString("publishedBy")).thenReturn(PUBLISHED_BY);
        when(document.get("publicationDate")).thenReturn(Timestamp.of(Date.from(PUBLICATION_DATE.toInstant())));
        when(document.getString("createdBy")).thenReturn(CREATED_BY);
        when(document.get("createDate")).thenReturn(Timestamp.of(Date.from(CREATE_DATE.toInstant())));
        when(document.getString("updatedBy")).thenReturn(UPDATED_BY);
        when(document.get("updateDate")).thenReturn(Timestamp.of(Date.from(UPDATE_DATE.toInstant())));

        // Act
        PostEntity result = PostEntityMapper.toPostEntity(document);

        // Assert
        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", ID)
                .hasFieldOrPropertyWithValue("title", TITLE)
                .hasFieldOrPropertyWithValue("content", CONTENT)
                .hasFieldOrPropertyWithValue("slug", SLUG)
                .hasFieldOrPropertyWithValue("category", CATEGORY)
                .hasFieldOrPropertyWithValue("publishedBy", PUBLISHED_BY)
                .hasFieldOrPropertyWithValue("publicationDate", PUBLICATION_DATE)
                .hasFieldOrPropertyWithValue("createdBy", CREATED_BY)
                .hasFieldOrPropertyWithValue("createDate", CREATE_DATE)
                .hasFieldOrPropertyWithValue("updatedBy", UPDATED_BY)
                .hasFieldOrPropertyWithValue("updateDate", UPDATE_DATE);
    }

    @Test
    public void testToPostEntityNull() {
        // Act
        PostEntity result = PostEntityMapper.toPostEntity(null);

        // Assert
        assertThat(result).isNull();
    }
}