package com.example.restclient.service;

import com.example.restclient.exception.PostNotFoundException;
import com.example.restclient.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(PostService.class)
class PostServiceTest {
    @Autowired
    MockRestServiceServer server;
    @Autowired
    PostService service;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllPosts() throws JsonProcessingException {
        //given
        List<Post> data = List.of(
                new Post(1, 1, "title1", "body1"),
                new Post(2, 2, "title2", "body2")
        );
        //when
        server.expect(requestTo("https://jsonplaceholder.typicode.com/posts"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(data), MediaType.APPLICATION_JSON));

        //then
        var result = service.findAll();
        assertEquals(2, result.size());
        assertEquals(data, result);
        assertEquals(data.getFirst(), result.getFirst());
    }

    @Test
    void shouldFindPostById() throws JsonProcessingException {
        //given
        Post data = new Post(1, 1, "title1", "body1");
        //when
        server.expect(requestTo("https://jsonplaceholder.typicode.com/posts/1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(data), MediaType.APPLICATION_JSON));

        //then
        var result = service.findById(1);
        assertEquals(data, result);
    }

    @Test
    void shouldThrowExceptionWhenPostNotFound() {
        //when
        server.expect(requestTo("https://jsonplaceholder.typicode.com/posts/33333"))
                .andRespond(withResourceNotFound());

        //then
        assertThrows(PostNotFoundException.class, () -> service.findById(33333));
    }
}