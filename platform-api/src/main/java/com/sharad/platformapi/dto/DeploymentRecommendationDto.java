package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record DeploymentRecommendationDto(

        int fearScore,
        String fearState,

        String crashLayer,
        int deployPercent,
        int remainingLayers,

        BigDecimal opportunityBuffer,
        BigDecimal deployAmount,
        BigDecimal remainingCash,

        String explanation

) {
}