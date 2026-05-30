package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EdgeAnalysisDto;
import com.sharad.platformapi.dto.EdgeSummaryDto;

import org.springframework.stereotype.Service;

@Service
public class EdgeSummaryService {

    private final EdgeAnalysisService edgeService;

    public EdgeSummaryService(
            EdgeAnalysisService edgeService
    ) {
        this.edgeService = edgeService;
    }

    public EdgeSummaryDto summarize(
            String symbol
    ) {

        EdgeAnalysisDto trend =
                edgeService.analyzeTrendEdge(
                        symbol
                );

        EdgeAnalysisDto macd =
                edgeService.analyzeMacdEdge(
                        symbol
                );

        EdgeAnalysisDto rsi =
                edgeService.analyzeRsiEdge(
                        symbol
                );

        EdgeAnalysisDto momentum =
                edgeService.analyzeMomentumEdge(
                        symbol
                );

        String strongestSignal =
                trend.winRatePct() >= macd.winRatePct()
                && trend.winRatePct() >= rsi.winRatePct()
                && trend.winRatePct() >= momentum.winRatePct()
                        ? "Price > SMA200"
                        : macd.winRatePct() >= rsi.winRatePct()
                        && macd.winRatePct() >= momentum.winRatePct()
                                ? "MACD Histogram > 0"
                                : rsi.winRatePct() >= momentum.winRatePct()
                                        ? "RSI > 50"
                                        : "Momentum > 20";

        return new EdgeSummaryDto(
                symbol,

                trend.winRatePct(),
                trend.sampleSize(),

                macd.winRatePct(),
                macd.sampleSize(),

                rsi.winRatePct(),
                rsi.sampleSize(),

                momentum.winRatePct(),
                momentum.sampleSize(),

                strongestSignal
        );
    }
}