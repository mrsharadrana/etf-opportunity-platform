package com.sharad.platformapi.dto;

public record ProbabilityResponseDto(

        String symbol,

        Integer confidence,

        Integer bullishSignals,

        Integer bearishSignals,

        String rating

) {
}