package com.sharad.platformapi.dto;

public record EtfScorecardDto(

        String symbol,

        Double price,

        Double momentumScore,

        String signal,

        Double rsi,

        Double sma50,

        Double sma200,

        Double ema50,

        Double macd,

        Double macdSignal,

        Double macdHistogram,

        Boolean above200DMA,

        String trendStrength

) {
}