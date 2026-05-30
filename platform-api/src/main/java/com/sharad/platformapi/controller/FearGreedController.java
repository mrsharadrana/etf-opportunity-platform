package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.FearGreedDto;
import com.sharad.platformapi.dto.FearGreedV2Dto;
import com.sharad.platformapi.service.FearGreedService;
import com.sharad.platformapi.service.FearGreedServiceV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FearGreedController {

    private final FearGreedService fearGreedService;

    private final FearGreedServiceV2 fearGreedServiceV2;

    public FearGreedController(
            FearGreedService fearGreedService,
            FearGreedServiceV2 fearGreedServiceV2
    ) {
        this.fearGreedService = fearGreedService;
        this.fearGreedServiceV2 = fearGreedServiceV2;
    }

    @GetMapping("/api/fear")
    public FearGreedDto getFearScore() {

        return fearGreedService.calculate();
    }
    
    @GetMapping("/api/fear/v2")
    public FearGreedV2Dto getFearScoreV2() {

        return FearGreedV2Dto.from(
                fearGreedServiceV2.calculate()
        );
    }
}