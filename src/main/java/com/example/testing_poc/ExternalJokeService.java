package com.example.testing_poc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.ResponseEntity;

@FeignClient(name = "external-joke-api", url = "https://official-joke-api.appspot.com")
public interface ExternalJokeService {
    @GetMapping("/random_joke")
    ResponseEntity<JokeDTO> randomJoke();
}