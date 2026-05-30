package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SmaResponseDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.service.indicators.ta4j.SmaIndicatorService;
import com.sharad.platformapi.service.indicators.ta4j.Ta4jSeriesBuilderService;

import org.springframework.web.bind.annotation.*;
import org.ta4j.core.BarSeries;

import java.util.List;

@RestController
@RequestMapping("/api/sma")
public class SmaController {

    private final ETFPriceHistoryRepository repository;
    private final Ta4jSeriesBuilderService seriesBuilder;
    private final SmaIndicatorService smaService;

    public SmaController(
            ETFPriceHistoryRepository repository,
            Ta4jSeriesBuilderService seriesBuilder,
            SmaIndicatorService smaService
    ) {
        this.repository = repository;
        this.seriesBuilder = seriesBuilder;
        this.smaService = smaService;
    }

    @GetMapping("/{symbol}")
    public SmaResponseDto getSma(
            @PathVariable String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        BarSeries series =
                seriesBuilder.buildSeries(
                        history
                );

        return new SmaResponseDto(
                symbol,
                smaService.calculateSma(
                        series,
                        50
                ),
                smaService.calculateSma(
                        series,
                        200
                )
        );
    }
}