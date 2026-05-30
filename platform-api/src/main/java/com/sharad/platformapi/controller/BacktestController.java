package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.BacktestResultDto;
import com.sharad.platformapi.service.BacktestService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backtest")
public class BacktestController {

    private final BacktestService service;

    public BacktestController(
            BacktestService service
    ) {
        this.service = service;
    }

    @GetMapping
    public BacktestResultDto runBacktest() {

        return service.runBacktest();
    }
}