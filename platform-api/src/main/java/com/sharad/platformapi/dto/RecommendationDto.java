package com.sharad.platformapi.dto;

public record RecommendationDto(

        String marketRegime,

        String recommendedETF,

        Integer confidence,

        String rating,

        Integer allocationPct

) {
}