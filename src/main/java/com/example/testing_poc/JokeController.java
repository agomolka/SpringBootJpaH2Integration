package com.example.testing_poc;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class JokeController {
    @Autowired
    private ExternalJokeService externalJokeService;

    @Autowired
    private JokeRepository jokeRepository;

    @GetMapping(value = "/random")
    public ResponseEntity<String> getJoke(
            @RequestParam @Size(min = 3, message = "Value must have at least 3 characters") String user) {
        ResponseEntity<JokeDTO> joke = externalJokeService.randomJoke();
        JokeHistoryEntry jokeHistoryEntry = JokeHistoryEntry
                .builder()
                .user(user)
                .joke(joke.toString())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body("Ok");
//        return ResponseEntity.status(HttpStatus.OK).body(joke.setup + " " + joke.punchline);
    }
    public JokeHistoryEntry saveJokeRequest(JokeHistoryEntry jokeHistoryEntry) {
        return jokeRepository.save(jokeHistoryEntry);
    }

    public List<JokeHistoryEntry> getAllJokeRequests() {
        return jokeRepository.findAll();
    }

    public Optional<JokeHistoryEntry> getJokeRequestById(Long id) {
        return jokeRepository.findById(id);
    }
}