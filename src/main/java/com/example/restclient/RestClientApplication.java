package com.example.restclient;

import com.example.restclient.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestClientApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner run(PostService service){
//        return args -> {
//            for (int i = 1; i <= 100; i++) {
//                System.out.println(service.findById(i));
//                System.out.println("==================================\n\n\n");
//            }
//        };
//    }

}
