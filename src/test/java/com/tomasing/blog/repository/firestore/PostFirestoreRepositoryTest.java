package com.tomasing.blog.repository.firestore;

import com.google.cloud.firestore.Firestore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PostFirestoreRepositoryTest {

    @Mock
    private Firestore firestore;

    @Test
    public void testConstructor() {
        new PostFirestoreRepository(firestore);
    }
}