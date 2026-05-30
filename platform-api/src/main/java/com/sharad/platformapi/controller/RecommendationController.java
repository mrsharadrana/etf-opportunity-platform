package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.RecommendationDto;
import com.sharad.platformapi.service.RecommendationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(
            RecommendationService service
    ) {
        this.service = service;
    }

    @GetMapping
    public RecommendationDto getRecommendation() {

        return service.getRecommendation();
    }
}