package com.tomasing.blog.service;

import com.tomasing.blog.repository.PostRepository;
import com.tomasing.blog.repository.model.PostEntity;
import com.tomasing.blog.service.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    private static final String CATEGORY = "category";
    private static final String SLUG = "slug";
    private static final String CONTENT_WITH_EXCERPT = "content with <!--more--> excerpt";
    private static final String STRIPPED_CONTENT = "content with ";

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void testGetPosts() {
        // Arrange
        when(postRepository.findAll())
                .thenReturn(Stream.of(PostEntity.builder().build()));

        // Act
        Stream<Post> result = postService.findAll();

        // Assert
        List<Post> resultList = result.collect(Collectors.toList());
        assertThat(resultList.size()).isEqualTo(1);
        assertThat(resultList).first().isNotNull();
    }

    @Test
    public void testGetPostsStrippedContent() {
        // Arrange
        when(postRepository.findAll())
                .thenReturn(Stream.of(PostEntity.builder().content(CONTENT_WITH_EXCERPT).build()));

        // Act
        Stream<Post> result = postService.findAll();

        // Assert
        List<Post> resultList = result.collect(Collectors.toList());
        assertThat(resultList.size()).isEqualTo(1);
        assertThat(resultList).first()
                .isNotNull()
                .hasFieldOrPropertyWithValue("content", STRIPPED_CONTENT);
    }

    @Test
    public void testGetPost() {
        // Arrange
        when(postRepository.findByCategoryAndSlug(CATEGORY, SLUG))
                .thenReturn(Optional.of(PostEntity.builder().build()));

        // Act
        Optional<Post> result = postService.findByCategoryAndSlug(CATEGORY, SLUG);

        // Assert
        assertThat(result)
                .isNotEmpty();
    }

    @Test
    public void testGetPostNotFound() {
        // Arrange
        when(postRepository.findByCategoryAndSlug(CATEGORY, SLUG))
                .thenReturn(Optional.empty());

        // Act
        Optional<Post> result = postService.findByCategoryAndSlug(CATEGORY, SLUG);

        // Assert
        assertThat(result).isEmpty();
    }
}
