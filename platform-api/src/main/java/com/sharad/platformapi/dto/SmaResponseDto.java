package com.sharad.platformapi.dto;

public record SmaResponseDto(
        String symbol,
        Double sma50,
        Double sma200
) {
}