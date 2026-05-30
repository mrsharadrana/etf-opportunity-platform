package com.sharad.platformapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MarketIndicatorDto(
        String indicatorName,
        LocalDate tradeDate,
        BigDecimal indicatorValue,
        String source
) {
}
