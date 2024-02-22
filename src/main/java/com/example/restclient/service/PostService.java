package com.example.restclient.service;

import com.example.restclient.exception.PostNotFoundException;
import com.example.restclient.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
public class PostService {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final RestClient restClient;

    public PostService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(BASE_URL).build();
    }

    public List<Post> findAll() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public Post findById(int id) {
        return restClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                    throw new PostNotFoundException(response.getStatusText());
                })
                .body(Post.class);
    }
}
