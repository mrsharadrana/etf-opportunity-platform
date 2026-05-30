package com.sharad.platformapi.service.ingestion;

import com.sharad.platformapi.dto.HistoricalPriceDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.service.EtfUniverseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricalDataLoaderService {

    private final ETFPriceHistoryRepository repository;

    private final EtfUniverseService etfUniverseService;

    private final MarketDataProvider marketDataProvider;

    public HistoricalDataLoaderService(
            ETFPriceHistoryRepository repository,
            EtfUniverseService etfUniverseService,
            MarketDataProvider marketDataProvider
    ) {
        this.repository = repository;
        this.etfUniverseService = etfUniverseService;
        this.marketDataProvider = marketDataProvider;
    }

    public void loadAll() {

        List<String> symbols =
                etfUniverseService.getActiveSymbols();

        for (String symbol : symbols) {

            loadSymbol(symbol);
        }
    }

    public void loadSymbol(
            String symbol
    ) {

        List<HistoricalPriceDto> history =
                marketDataProvider.getHistory(
                        symbol
                );

        for (HistoricalPriceDto row : history) {

            boolean exists =
                    repository.existsBySymbolAndTradeDate(
                            symbol,
                            row.tradeDate()
                    );

            if (exists) {
                continue;
            }

            ETFPriceHistory entity =
                    new ETFPriceHistory();

            entity.setSymbol(symbol);

            entity.setTradeDate(
                    row.tradeDate()
            );

            entity.setOpenPrice(
                    row.openPrice()
            );

            entity.setHighPrice(
                    row.highPrice()
            );

            entity.setLowPrice(
                    row.lowPrice()
            );

            entity.setClosePrice(
                    row.closePrice()
            );

            entity.setVolume(
                    row.volume()
            );

            repository.save(entity);
        }
    }
}