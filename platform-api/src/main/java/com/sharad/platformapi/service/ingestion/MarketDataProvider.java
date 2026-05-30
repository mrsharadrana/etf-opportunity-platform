package com.sharad.platformapi.service.ingestion;

import com.sharad.platformapi.dto.HistoricalPriceDto;

import java.util.List;

public interface MarketDataProvider {

    List<HistoricalPriceDto> getHistory(
            String symbol
    );
}