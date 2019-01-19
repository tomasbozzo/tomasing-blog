package com.tomasing.blog.service;

import com.tomasing.blog.repository.PostRepository;
import com.tomasing.blog.repository.model.PostEntity;
import com.tomasing.blog.service.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String CATEGORY = "category";
    private static final String SLUG = "slug";
    private static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    private static final String CREATED_BY = "createdBy";
    private static final ZonedDateTime UPDATE_DATE = ZonedDateTime.now();
    private static final String UPDATED_BY = "updatedBy";
    private static final String PUBLISHED_BY = "publishedBy";
    private static final ZonedDateTime PUBLICATION_DATE = ZonedDateTime.now();

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void testGetPosts() {
        // Given
        Iterable<PostEntity> posts = createPostsEntities();
        when(postRepository.findAll()).thenReturn(posts);

        // When
        Stream<Post> result = postService.findAll();

        // Then
        List<Post> resultList = result.collect(Collectors.toList());
        assertThat(resultList.size()).isEqualTo(1);
        assertThat(resultList).first().isEqualTo(createPost());
    }

    @Test
    public void testGetPost() {
        // Given
        Optional<PostEntity> postEntity = Optional.of(createPostEntity());
        when(postRepository.findByCategoryAndSlug(CATEGORY, SLUG)).thenReturn(postEntity);

        // When
        Optional<Post> result = postService.findByCategoryAndSlug(CATEGORY, SLUG);

        // Then
        assertThat(result)
                .isNotEmpty()
                .contains(createPost());
    }

    @Test
    public void testGetPostNotFound() {
        // Given
        Optional<PostEntity> postEntity = Optional.empty();
        when(postRepository.findByCategoryAndSlug(CATEGORY, SLUG)).thenReturn(postEntity);

        // When
        Optional<Post> result = postService.findByCategoryAndSlug(CATEGORY, SLUG);

        // Then
        assertThat(result).isEmpty();
    }

    private Iterable<PostEntity> createPostsEntities() {
        return Collections.singletonList(createPostEntity());
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
