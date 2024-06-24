package com.example.testing_poc;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JokeController {
    @Autowired
    private ExternalJokeService externalJokeService;

    @Autowired
    private JokeRepository jokeRepository;

    @GetMapping(value = "/random")
    public ResponseEntity<JokeResponseDTO> getJoke(
            @RequestParam @Size(min = 3, message = "Value must have at least 3 characters") String user) {
        ResponseEntity<JokeDTO> joke = externalJokeService.randomJoke();
        JokeHistoryEntry jokeHistoryEntry = JokeHistoryEntry
                .builder()
                .user(user)
                .joke(joke.getBody().fullJoke())
                .build();
        JokeHistoryEntry savedEntry = jokeRepository.save(jokeHistoryEntry);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JokeResponseDTO(savedEntry));
    }
}