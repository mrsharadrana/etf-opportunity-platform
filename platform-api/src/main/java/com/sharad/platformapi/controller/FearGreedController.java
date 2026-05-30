package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.FearGreedDto;
import com.sharad.platformapi.service.FearGreedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FearGreedController {

    private final FearGreedService fearGreedService;

    public FearGreedController(
            FearGreedService fearGreedService
    ) {
        this.fearGreedService = fearGreedService;
    }

    @GetMapping("/api/fear")
    public FearGreedDto getFearScore() {

        return fearGreedService.calculate();
    }
}