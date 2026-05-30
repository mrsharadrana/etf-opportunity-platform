package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.MarketStateDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class MarketStateService {

    private final ETFPriceHistoryRepository repository;

    public MarketStateService(
            ETFPriceHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public MarketStateDto getMarketState() {

        String india =
                calculateState("NIFTYBEES.NS");

        String usa =
                calculateState("MON100.NS");

        String china =
                calculateState("HNGSNGBEES.NS");

        String gold =
                calculateState("GOLDBEES.NS");

        String silver =
                calculateState("SILVERIETF.NS");

        return new MarketStateDto(
                india,
                usa,
                china,
                gold,
                silver
        );
    }

    private String calculateState(
            String symbol
    ) {

        ETFPriceHistory latest =
                repository
                        .findBySymbolOrderByTradeDateDesc(
                                symbol
                        )
                        .stream()
                        .findFirst()
                        .orElse(null);

        if (latest == null
                || latest.getSma200() == null) {

            return "UNKNOWN";
        }

        double close =
                latest.getClosePrice()
                        .doubleValue();

        double sma200 =
                latest.getSma200()
                        .doubleValue();

        double pct =
                ((close - sma200)
                        / sma200)
                        * 100.0;

        if (pct >= 5) {
            return "BULL";
        }

        if (pct >= 0) {
            return "RECOVERY";
        }

        if (pct >= -5) {
            return "CORRECTION";
        }

        return "PANIC";
    }
}