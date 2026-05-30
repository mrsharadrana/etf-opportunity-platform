package com.sharad.platformapi.dto;

import java.time.LocalDate;

public record SelectorHistoryDto(

        LocalDate tradeDate,

        String marketRegime,

        String recommendedETF

) {
}