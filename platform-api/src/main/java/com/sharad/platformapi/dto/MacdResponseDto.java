package com.sharad.platformapi.dto;

public record MacdResponseDto(

        String symbol,

        Double macd,

        Double signal,

        Double histogram

) {
}