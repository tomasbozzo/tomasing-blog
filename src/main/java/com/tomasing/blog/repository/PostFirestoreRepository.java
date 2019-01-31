package com.tomasing.blog.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.tomasing.blog.repository.exception.RepositoryException;
import com.tomasing.blog.repository.mappers.PostEntityMapper;
import com.tomasing.blog.repository.model.PostEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 * Stores Post entities in Google Firestore.
 */
@Repository
public class PostFirestoreRepository implements PostRepository {

    private static final String POSTS_COLLECTION = "posts";
    private static final String CATEGORY_FIELD = "category";
    private static final String SLUG_FIELD = "slug";
    private static final String PUBLICATION_DATE_FIELD = "publicationDate";

    private final Firestore firestore;

    public PostFirestoreRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Optional<PostEntity> findByCategoryAndSlug(@Nonnull String category, @Nonnull String slug) {
        try {
            return firestore.collection(POSTS_COLLECTION)
                    .whereEqualTo(CATEGORY_FIELD, category)
                    .whereEqualTo(SLUG_FIELD, slug)
                    .get()
                    .get()
                    .getDocuments().stream()
                    .map(PostEntityMapper::toPostEntity)
                    .findAny();
        } catch (InterruptedException | ExecutionException e) {
            throw new RepositoryException("Could not find a post by category '" + category + "' and slug '" + slug + "'", e);
        }
    }

    @Nonnull
    @Override
    public Stream<PostEntity> findAll() {
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(POSTS_COLLECTION)
                    .orderBy(PUBLICATION_DATE_FIELD, Query.Direction.DESCENDING)
                    .get();

            return query.get().getDocuments().stream()
                    .map(PostEntityMapper::toPostEntity);
        } catch (InterruptedException | ExecutionException e) {
            throw new RepositoryException("Could not find all posts", e);
        }
    }
}
