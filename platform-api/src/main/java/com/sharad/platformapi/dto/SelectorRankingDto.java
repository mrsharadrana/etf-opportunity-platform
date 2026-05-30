package com.sharad.platformapi.dto;

public record SelectorRankingDto(

        String symbol,

        Integer rank,

        Double momentumScore,

        String signal

) {
}