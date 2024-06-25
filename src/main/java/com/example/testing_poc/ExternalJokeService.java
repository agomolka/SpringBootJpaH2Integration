package com.example.testing_poc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.ResponseEntity;

@FeignClient(name = "external-joke-api", url = "${joke.api.base-url}")
public interface ExternalJokeService {
    @GetMapping("/random_joke")
    ResponseEntity<JokeDTO> randomJoke();
}
// Response format:
// {
//  "type": "general",
//  "setup": "I had a dream that I was a muffler last night.",
//  "punchline": "I woke up exhausted!",
//  "id": 54
// }