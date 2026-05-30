package com.sharad.platformapi.dto;

import java.time.LocalDate;

public record ScoreHistoryDto(
        LocalDate tradeDate,
        Integer probabilityScore,
        Integer relativeStrengthScore,
        Integer etfScore
) {
}