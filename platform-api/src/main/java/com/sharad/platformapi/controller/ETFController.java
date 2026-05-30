package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.RankingDto;

import com.sharad.platformapi.service.ETFHistoryService;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ETFController {

    private final ETFHistoryService historyService;

    public ETFController(
            ETFHistoryService historyService
    ) {
        this.historyService = historyService;
    }

    @GetMapping("/etf/{symbol}")
    public List<RankingDto> etfHistory(
            @PathVariable String symbol
    ) {

        return historyService.getETFHistory(
                symbol
        );
    }
}