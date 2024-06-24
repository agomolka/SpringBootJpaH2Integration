package com.example.testing_poc;

import lombok.Getter;

@Getter
public class JokeResponseDTO {
    private final String joke;

    public JokeResponseDTO(JokeHistoryEntry jokeHistoryEntry) {
        this.joke = jokeHistoryEntry.getJoke();
    }
}
