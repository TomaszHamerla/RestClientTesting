package com.example.restclient.controller;

import com.example.restclient.model.Post;
import com.example.restclient.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping
    List<Post> findAll() throws InterruptedException {
        Thread.sleep(1000);
        log.info("Thread: {}", Thread.currentThread().getName());
        return postService.findAll();
    }

    @GetMapping("/{id}")
    Post findById(@PathVariable int id) {
        log.info("Thread: {}", Thread.currentThread().getName());
        return postService.findById(id);
    }
}
