package com.tomasing.blog.web.controller;

import com.tomasing.blog.repository.exception.RepositoryException;
import com.tomasing.blog.service.PostService;
import com.tomasing.blog.service.model.Post;
import com.tomasing.blog.web.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    private static final String CATEGORY = "category";
    private static final String SLUG = "slug";
    private static final String EXCEPTION_MESSAGE = "exception message";
    private static final Throwable CAUSE = new Exception();

    @MockBean
    private PostService postService;

    @Autowired
    private PostController postController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setControllerAdvice(ExceptionController.class)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void testGetPosts() throws Exception {
        // Arrange
        when(postService.findAll())
                .thenReturn(Stream.of(Post.builder().build()));

        // Act
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPostsException() throws Exception {
        // Arrange
        when(postService.findAll())
                .thenThrow(new RepositoryException(EXCEPTION_MESSAGE, CAUSE));

        // Act
        mockMvc.perform(get("/posts"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetPost() throws Exception {
        // Arrange
        when(postService.findByCategoryAndSlug(CATEGORY, SLUG))
                .thenReturn(Optional.of(Post.builder().build()));

        // Act
        mockMvc.perform(get(MessageFormat.format("/categories/{0}/posts/{1}", CATEGORY, SLUG)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPostNotFound() throws Exception {
        // Arrange
        when(postController.findAll()).thenThrow(new NotFoundException(EXCEPTION_MESSAGE));

        // Act
        mockMvc.perform(get(MessageFormat.format("/categories/{0}/posts/{1}", CATEGORY, SLUG)))
                .andExpect(status().isNotFound());
    }
}