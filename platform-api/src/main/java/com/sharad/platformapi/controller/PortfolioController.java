package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.PortfolioResponseDto;
import com.sharad.platformapi.service.PortfolioAllocatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    private final PortfolioAllocatorService service;

    public PortfolioController(
            PortfolioAllocatorService service
    ) {
        this.service = service;
    }

    @GetMapping("/api/portfolio")
    public PortfolioResponseDto getPortfolio() {
        return service.allocate();
    }
}