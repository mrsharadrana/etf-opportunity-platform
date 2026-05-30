package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.MarketRegimeDto;

import com.sharad.platformapi.service.regime.MarketRegimeService;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MarketRegimeController {

    private final MarketRegimeService marketRegimeService;

    public MarketRegimeController(
            MarketRegimeService marketRegimeService
    ) {
        this.marketRegimeService =
                marketRegimeService;
    }

    @GetMapping("/market-regime")
    public MarketRegimeDto marketRegime() {

        return marketRegimeService
                .getLatestRegime();
    }
}