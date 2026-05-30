package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.MarketStateDto;
import com.sharad.platformapi.service.MarketStateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketStateController {

    private final MarketStateService marketStateService;

    public MarketStateController(
            MarketStateService marketStateService
    ) {
        this.marketStateService = marketStateService;
    }

    @GetMapping("/api/market-state")
    public MarketStateDto getMarketState() {

        return marketStateService
                .getMarketState();
    }
}