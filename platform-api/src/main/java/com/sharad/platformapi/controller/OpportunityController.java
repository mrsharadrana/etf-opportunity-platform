package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.OpportunityResponseDto;
import com.sharad.platformapi.service.OpportunityEngineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpportunityController {

    private final OpportunityEngineService service;

    public OpportunityController(
            OpportunityEngineService service
    ) {
        this.service = service;
    }

    @GetMapping("/api/opportunities")
    public OpportunityResponseDto getOpportunities() {

        return service.getOpportunities();
    }
}