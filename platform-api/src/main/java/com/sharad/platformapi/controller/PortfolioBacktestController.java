package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.PortfolioBacktestDto;
import com.sharad.platformapi.service.PortfolioBacktestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioBacktestController {

    private final PortfolioBacktestService service;

    public PortfolioBacktestController(
            PortfolioBacktestService service
    ) {
        this.service = service;
    }

    @GetMapping("/api/portfolio/backtest")
    public PortfolioBacktestDto run() {

        return service.run();
    }
}