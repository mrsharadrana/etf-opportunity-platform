package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.EtfScoreDto;
import com.sharad.platformapi.service.EtfScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score")
public class EtfScoreController {

    private final EtfScoreService service;

    public EtfScoreController(
            EtfScoreService service
    ) {
        this.service = service;
    }

    @GetMapping("/{symbol}")
    public EtfScoreDto getScore(
            @PathVariable String symbol
    ) {
        return service.calculate(
                symbol
        );
    }
}