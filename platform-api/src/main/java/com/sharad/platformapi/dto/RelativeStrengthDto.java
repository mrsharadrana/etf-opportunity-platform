package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record RelativeStrengthDto(
        String symbol,
        BigDecimal momentumScore,
        Integer relativeStrengthRank,
        Integer relativeStrengthScore
) {
}