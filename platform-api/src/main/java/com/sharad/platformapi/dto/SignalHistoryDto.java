package com.sharad.platformapi.dto;

import java.time.LocalDate;

public record SignalHistoryDto(

        LocalDate tradeDate,

        String selectedEtf,

        String marketRegime

) {
}