package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.ProbabilityResponseDto;
import com.sharad.platformapi.service.ProbabilityEngineService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/probability")
public class ProbabilityController {

    private final ProbabilityEngineService service;

    public ProbabilityController(
            ProbabilityEngineService service
    ) {
        this.service = service;
    }

    @GetMapping("/{symbol}")
    public ProbabilityResponseDto analyze(
            @PathVariable String symbol
    ) {

        return service.analyze(
                symbol
        );
    }
}