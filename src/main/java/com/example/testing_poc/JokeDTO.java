package com.example.testing_poc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JokeDTO {
    private String type;
    private String setup;
    private String punchline;
    private Long id;
}