package com.sharad.platformapi.dto;

public record EdgeSummaryDto(

        String symbol,

        Double trendWinRate,
        Integer trendSampleSize,

        Double macdWinRate,
        Integer macdSampleSize,

        Double rsiWinRate,
        Integer rsiSampleSize,

        Double momentumWinRate,
        Integer momentumSampleSize,

        String strongestSignal

) {
}