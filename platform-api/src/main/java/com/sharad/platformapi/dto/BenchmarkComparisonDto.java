package com.sharad.platformapi.dto;

public record BenchmarkComparisonDto(

        Double strategyReturn,

        Double niftyBeesReturn,

        Double mon100Return,

        Double goldBeesReturn,

        String bestPerformer

) {
}