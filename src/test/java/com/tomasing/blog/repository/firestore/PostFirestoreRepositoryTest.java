package com.tomasing.blog.repository.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.tomasing.blog.repository.PostFirestoreRepository;
import com.tomasing.blog.repository.exception.RepositoryException;
import com.tomasing.blog.repository.model.PostEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostFirestoreRepositoryTest {

    private static final String POSTS_COLLECTION = "posts";
    private static final String CATEGORY_FIELD = "category";
    private static final String CATEGORY = "category";
    private static final String SLUG_FIELD = "slug";
    private static final String SLUG = "slug";
    private static final String PUBLICATION_DATE_FIELD = "publicationDate";

    @Mock
    private Firestore firestore;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private ApiFuture<QuerySnapshot> apiFuture;

    @Mock
    private QuerySnapshot querySnapshot;

    @Mock
    private QueryDocumentSnapshot queryDocumentSnapshot;

    @Mock
    private Query query;

    @InjectMocks
    private PostFirestoreRepository repository;

    @Test
    public void testFindAll() throws ExecutionException, InterruptedException {
        // Arrange
        when(firestore.collection(POSTS_COLLECTION))
                .thenReturn(collectionReference);
        when(collectionReference.orderBy(PUBLICATION_DATE_FIELD, Query.Direction.DESCENDING))
                .thenReturn(collectionReference);
        when(collectionReference.get())
                .thenReturn(apiFuture);
        when(apiFuture.get())
                .thenReturn(querySnapshot);
        when(querySnapshot.getDocuments())
                .thenReturn(singletonList(queryDocumentSnapshot));

        // Act
        Stream<PostEntity> result = repository.findAll();

        // Assert
        assertThat(result).hasSize(1);
    }

    @Test
    public void testFindAllException() throws ExecutionException, InterruptedException {
        // Arrange
        when(firestore.collection(POSTS_COLLECTION))
                .thenReturn(collectionReference);
        when(collectionReference.orderBy(PUBLICATION_DATE_FIELD, Query.Direction.DESCENDING))
                .thenReturn(collectionReference);
        when(collectionReference.get())
                .thenReturn(apiFuture);

        Exception exception = new InterruptedException();
        when(apiFuture.get()).thenThrow(exception);

        // Act
        assertThatThrownBy(() -> repository.findAll())
                .isInstanceOf(RepositoryException.class)
                .hasMessage("Could not find all posts")
                .hasCause(exception);
    }

    @Test
    public void testFindByCategoryAndSlug() throws ExecutionException, InterruptedException {
        // Arrange
        when(firestore.collection(POSTS_COLLECTION)).thenReturn(collectionReference);
        when(collectionReference.whereEqualTo(CATEGORY_FIELD, CATEGORY)).thenReturn(query);
        when(query.whereEqualTo(SLUG_FIELD, SLUG)).thenReturn(query);
        when(query.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(querySnapshot);
        when(querySnapshot.getDocuments()).thenReturn(singletonList(queryDocumentSnapshot));

        // Act
        Optional<PostEntity> result = repository.findByCategoryAndSlug(CATEGORY, SLUG);

        // Assert
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testFindByCategoryAndSlugNotFound() throws ExecutionException, InterruptedException {
        // Arrange
        when(firestore.collection(POSTS_COLLECTION)).thenReturn(collectionReference);
        when(collectionReference.whereEqualTo(CATEGORY_FIELD, CATEGORY)).thenReturn(query);
        when(query.whereEqualTo(SLUG_FIELD, SLUG)).thenReturn(query);
        when(query.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(querySnapshot);
        when(querySnapshot.getDocuments()).thenReturn(emptyList());

        // Act
        Optional<PostEntity> result = repository.findByCategoryAndSlug(CATEGORY, SLUG);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByCategoryAndSlugException() throws ExecutionException, InterruptedException {
        // Arrange
        when(firestore.collection(POSTS_COLLECTION)).thenReturn(collectionReference);
        when(collectionReference.whereEqualTo("category", CATEGORY)).thenReturn(query);
        when(query.whereEqualTo("slug", SLUG)).thenReturn(query);
        when(query.get()).thenReturn(apiFuture);

        Exception exception = new InterruptedException();
        when(apiFuture.get()).thenThrow(exception);

        // Act
        assertThatThrownBy(() -> repository.findByCategoryAndSlug(CATEGORY, SLUG))
                .isInstanceOf(RepositoryException.class)
                .hasMessage("Could not find a post by category 'category' and slug 'slug'")
                .hasCause(exception);
    }
}