package com.sharad.platformapi.dto;

public record ScreenerRowDto(

        Integer rank,

        String symbol,

        Integer totalScore,

        Integer probabilityScore,

        Integer relativeStrengthScore,

        String rating

) {
}