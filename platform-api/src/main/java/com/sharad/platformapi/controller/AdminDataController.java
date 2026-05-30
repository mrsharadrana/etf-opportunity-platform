package com.sharad.platformapi.controller;

import com.sharad.platformapi.service.IndiaVixIngestionService;
import com.sharad.platformapi.service.ingestion.HistoricalDataLoaderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminDataController {

    private final HistoricalDataLoaderService loaderService;
    private final IndiaVixIngestionService indiaVixIngestionService;

    public AdminDataController(
            HistoricalDataLoaderService loaderService,
            IndiaVixIngestionService indiaVixIngestionService
    ) {
        this.loaderService = loaderService;
        this.indiaVixIngestionService = indiaVixIngestionService;
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

    @PostMapping("/ingest/india-vix")
    public String ingestIndiaVix() {

        indiaVixIngestionService.ingestLatestIndiaVix();

        return "India VIX ingestion started";
    }
}