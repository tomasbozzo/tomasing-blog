package com.tomasing.blog.web.controller.admin;

import com.tomasing.blog.service.PostService;
import com.tomasing.blog.service.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminHomeController.class)
public class AdminHomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    public void testHome() throws Exception {
        // Given
        Stream<Post> posts = createPosts();
        when(postService.findAll()).thenReturn(posts);

        // When
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    private Stream<Post> createPosts() {
        return Stream.of(Post.builder()
                .build());
    }
}