package com.sharad.platformapi.dto;

public record EtfScoreDto(

        String symbol,

        Integer probabilityScore,

        Integer relativeStrengthScore,

        Integer totalScore,

        String rating
) {
}