package com.example.testing_poc;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<JokeHistoryEntry, Long> {
}