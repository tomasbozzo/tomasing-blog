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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    private static final Long ID = 1L;
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String SLUG = "slug";
    private static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    private static final String CREATED_BY = "createdBy";
    private static final ZonedDateTime UPDATE_DATE = ZonedDateTime.now();
    private static final String UPDATED_BY = "updatedBy";

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void testGetPosts() {
        // Given
        Iterable<PostEntity> posts = createPosts();
        when(postRepository.findAll()).thenReturn(posts);

        // When
        Stream<Post> result = postService.getPosts();

        // When
        List<Post> resultList = result.collect(Collectors.toList());
        assertThat(resultList.size()).isEqualTo(1);
        assertThat(resultList).first().isEqualTo(Post.builder()
                .id(ID)
                .title(TITLE)
                .content(CONTENT)
                .slug(SLUG)
                .createDate(CREATE_DATE)
                .createdBy(CREATED_BY)
                .updateDate(UPDATE_DATE)
                .updatedBy(UPDATED_BY)
                .build());
    }

    private Iterable<PostEntity> createPosts() {
        return Collections.singletonList(PostEntity.builder()
                .id(ID)
                .title(TITLE)
                .content(CONTENT)
                .slug(SLUG)
                .createDate(CREATE_DATE)
                .createdBy(CREATED_BY)
                .updateDate(UPDATE_DATE)
                .updatedBy(UPDATED_BY)
                .build());
    }
}