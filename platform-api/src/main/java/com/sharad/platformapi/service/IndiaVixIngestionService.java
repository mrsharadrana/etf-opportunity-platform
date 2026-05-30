package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.MarketIndicatorDto;
import com.sharad.platformapi.service.ingestion.IndiaVixMarketDataProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class IndiaVixIngestionService {

    private static final String INDIA_VIX = "INDIA_VIX";

    private static final String SOURCE = "INDIA_VIX_INGESTION";

    private final MarketIndicatorService marketIndicatorService;
    private final IndiaVixMarketDataProvider indiaVixMarketDataProvider;

    public IndiaVixIngestionService(
            MarketIndicatorService marketIndicatorService,
            IndiaVixMarketDataProvider indiaVixMarketDataProvider
    ) {
        this.marketIndicatorService = Objects.requireNonNull(
                marketIndicatorService,
                "marketIndicatorService must not be null"
        );
        this.indiaVixMarketDataProvider = Objects.requireNonNull(
                indiaVixMarketDataProvider,
                "indiaVixMarketDataProvider must not be null"
        );
    }

    public void ingestDailyClose(
            LocalDate tradeDate,
            BigDecimal closeValue
    ) {
        Objects.requireNonNull(tradeDate, "tradeDate must not be null");
        Objects.requireNonNull(closeValue, "closeValue must not be null");

        MarketIndicatorDto dto = new MarketIndicatorDto(
                INDIA_VIX,
                tradeDate,
                closeValue,
                SOURCE
        );

        marketIndicatorService.save(dto);
    }

    public void ingestLatestIndiaVix() {
        BigDecimal latestClose = indiaVixMarketDataProvider.fetchLatestClose();
        LocalDate tradeDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        ingestDailyClose(tradeDate, latestClose);
    }
}
