package com.tomasing.blog.repository.mappers;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.tomasing.blog.repository.model.PostEntity;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Maps objects to PostEntity.
 */
public class PostEntityMapper {

    private static final String CATEGORY_FIELD = "category";
    private static final String SLUG_FIELD = "slug";
    private static final String TITLE_FIELD = "title";
    private static final String CONTENT_FIELD = "content";
    private static final String CREATED_BY_FIELD = "createdBy";
    private static final String CREATE_DATE_FIELD = "createDate";
    private static final String PUBLISHED_BY_FIELD = "publishedBy";
    private static final String PUBLICATION_DATE_FIELD = "publicationDate";
    private static final String UPDATED_BY_FIELD = "updatedBy";
    private static final String UPDATE_DATE_FIELD = "updateDate";

    /**
     * Converts a Firestore document to a PostEntity object.
     *
     * @param document The Firestore document to convert.
     * @return The converted PostEntity object.
     */
    public static PostEntity toPostEntity(@Nullable QueryDocumentSnapshot document) {
        return Optional.ofNullable(document)
                .map(d -> PostEntity.builder()
                        .id(document.getId())
                        .title(document.getString(TITLE_FIELD))
                        .category(document.getString(CATEGORY_FIELD))
                        .slug(document.getString(SLUG_FIELD))
                        .content(document.getString(CONTENT_FIELD))
                        .publishedBy(document.getString(PUBLISHED_BY_FIELD))
                        .publicationDate(toZonedDateTime((Timestamp) document.get(PUBLICATION_DATE_FIELD)))
                        .createdBy(document.getString(CREATED_BY_FIELD))
                        .createDate(toZonedDateTime((Timestamp) document.get(CREATE_DATE_FIELD)))
                        .updatedBy(document.getString(UPDATED_BY_FIELD))
                        .updateDate(toZonedDateTime((Timestamp) document.get(UPDATE_DATE_FIELD)))
                        .build())
                .orElse(null);

    }

    private static ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        return Optional.ofNullable(timestamp)
                .map(t -> t.toDate().toInstant().atZone(ZoneId.systemDefault()))
                .orElse(null);
    }
}
