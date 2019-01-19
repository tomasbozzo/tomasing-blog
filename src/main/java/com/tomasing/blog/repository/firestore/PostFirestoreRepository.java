package com.tomasing.blog.repository.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.tomasing.blog.repository.PostRepository;
import com.tomasing.blog.repository.model.PostEntity;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostFirestoreRepository implements PostRepository {

    private static final String POSTS_COLLECTION = "posts";
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

    private final Firestore firestore;

    public PostFirestoreRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    @SneakyThrows
    public Optional<PostEntity> findByCategoryAndSlug(String category, String slug) {
        ApiFuture<QuerySnapshot> query = firestore.collection(POSTS_COLLECTION)
                .whereEqualTo(CATEGORY_FIELD, category)
                .whereEqualTo(SLUG_FIELD, slug)
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        return documents.stream()
                .map(this::toPostEntity)
                .filter(p -> p.getCategory().equals(category) && p.getSlug().equals(slug))
                .findAny();
    }

    @Override
    @SneakyThrows
    public Iterable<PostEntity> findAll() {
        ApiFuture<QuerySnapshot> query = firestore.collection(POSTS_COLLECTION).get();

        return query.get().getDocuments().stream()
                .map(this::toPostEntity)
                .collect(Collectors.toList());
    }

    private PostEntity toPostEntity(QueryDocumentSnapshot document) {
        return PostEntity.builder()
                .id(document.getId())
                .title(document.getString(TITLE_FIELD))
                .category(document.getString(CATEGORY_FIELD))
                .slug(document.getString(SLUG_FIELD))
                .content(document.getString(CONTENT_FIELD))
                .publishedBy(document.getString(PUBLISHED_BY_FIELD))
                .publicationDate(toZonedDateTime(document.getDate(PUBLICATION_DATE_FIELD)))
                .createdBy(document.getString(CREATED_BY_FIELD))
                .createDate(toZonedDateTime(document.getDate(CREATE_DATE_FIELD)))
                .updatedBy(document.getString(UPDATED_BY_FIELD))
                .updateDate(toZonedDateTime(document.getDate(UPDATE_DATE_FIELD)))
                .build();
    }

    private ZonedDateTime toZonedDateTime(Date date) {
        return Optional.ofNullable(date)
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()))
                .orElse(null);
    }
}
