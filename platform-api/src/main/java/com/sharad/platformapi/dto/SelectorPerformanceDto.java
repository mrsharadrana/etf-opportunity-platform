package com.sharad.platformapi.dto;

public record SelectorPerformanceDto(

        Double strategyReturn,

        Double cagr,

        Double winRate,

        Double maxDrawdown,

        String bestETF,

        Double bestETFReturn

) {
}