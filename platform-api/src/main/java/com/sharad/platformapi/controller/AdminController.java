package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.HistoryGenerationResultDto;
import com.sharad.platformapi.service.HistoricalMarketRegimeService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final HistoricalMarketRegimeService service;

    public AdminController(
            HistoricalMarketRegimeService service
    ) {
        this.service = service;
    }

    @PostMapping("/generate-market-regime-history")
    public HistoryGenerationResultDto generateHistory() {

        return service.generateHistoricalRegimes();
    }
}