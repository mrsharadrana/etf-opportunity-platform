package com.sharad.platformapi.controller;

import com.sharad.platformapi.service.ingestion.HistoricalDataLoaderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminDataController {

    private final HistoricalDataLoaderService loaderService;

    public AdminDataController(
            HistoricalDataLoaderService loaderService
    ) {
        this.loaderService = loaderService;
    }

    @PostMapping("/backfill")
    public String backfillAll() {

        loaderService.loadAll();

        return "Backfill started for all ETFs";
    }

    @PostMapping("/backfill/{symbol}")
    public String backfillSymbol(
            @PathVariable String symbol
    ) {

        loaderService.loadSymbol(symbol);

        return "Backfill started for " + symbol;
    }
}