package com.sharad.platformapi.controller;

import com.sharad.platformapi.service.ScorePersistenceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScorePersistenceController {

    private final ScorePersistenceService service;

    public ScorePersistenceController(
            ScorePersistenceService service
    ) {
        this.service = service;
    }

    @PostMapping("/api/admin/persist-scores")
    public String persistScores() {

        service.persistScores();

        return "Scores Persisted";
    }
}