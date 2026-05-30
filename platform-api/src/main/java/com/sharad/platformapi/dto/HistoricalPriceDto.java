package com.sharad.platformapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HistoricalPriceDto(

        LocalDate tradeDate,

        BigDecimal openPrice,

        BigDecimal highPrice,

        BigDecimal lowPrice,

        BigDecimal closePrice,

        Long volume

) {
}