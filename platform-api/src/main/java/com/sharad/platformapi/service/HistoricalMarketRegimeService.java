package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.HistoryGenerationResultDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.entity.MarketRegime;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.repository.MarketRegimeRepository;
import com.sharad.platformapi.service.indicators.ta4j.HistoricalIndicatorService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistoricalMarketRegimeService {

    private static final String NIFTY_SYMBOL =
            "NIFTYBEES.NS";

    private final ETFPriceHistoryRepository etfRepository;

    private final MarketRegimeRepository marketRegimeRepository;

    private final HistoricalIndicatorService historicalIndicatorService;

    public HistoricalMarketRegimeService(
            ETFPriceHistoryRepository etfRepository,
            MarketRegimeRepository marketRegimeRepository,
            HistoricalIndicatorService historicalIndicatorService
    ) {
        this.etfRepository = etfRepository;
        this.marketRegimeRepository = marketRegimeRepository;
        this.historicalIndicatorService =
                historicalIndicatorService;
    }

    public HistoryGenerationResultDto generateHistoricalRegimes() {

        List<ETFPriceHistory> allHistory =
                etfRepository.findAll();

        Map<LocalDate, List<ETFPriceHistory>> byDate =
                allHistory.stream()
                        .collect(
                                Collectors.groupingBy(
                                        ETFPriceHistory::getTradeDate
                                )
                        );

        marketRegimeRepository.deleteAll();

        int generated = 0;

        for (Map.Entry<LocalDate, List<ETFPriceHistory>> entry
                : byDate.entrySet()) {

            LocalDate tradeDate =
                    entry.getKey();

            List<ETFPriceHistory> dayData =
                    entry.getValue();

            ETFPriceHistory nifty =
                    dayData.stream()
                            .filter(
                                    e -> NIFTY_SYMBOL.equalsIgnoreCase(
                                            e.getSymbol()
                                    )
                            )
                            .findFirst()
                            .orElse(null);

            if (nifty == null) {
                continue;
            }

            ETFPriceHistory topRanked =
                    dayData.stream()
                            .filter(
                                    e -> e.getRank() != null
                                            && e.getRank() == 1
                            )
                            .findFirst()
                            .orElse(null);

            if (topRanked == null) {
                continue;
            }

            double historicalSma200 =
                    historicalIndicatorService
                            .getHistoricalSma(
                                    NIFTY_SYMBOL,
                                    50,
                                    tradeDate
                            );

            if (historicalSma200 == 0.0) {
                continue;
            }

            double niftyClose =
                    nifty.getClosePrice()
                            .doubleValue();

            String regime =
                    niftyClose > historicalSma200
                            ? "RISK_ON"
                            : "RISK_OFF";

            MarketRegime marketRegime =
                    new MarketRegime();

            marketRegime.setTradeDate(
                    tradeDate
            );

            marketRegime.setMarketRegime(
                    regime
            );

            marketRegime.setTopRankedEtf(
                    topRanked.getSymbol()
            );

            marketRegimeRepository.save(
                    marketRegime
            );

            generated++;
        }

        return new HistoryGenerationResultDto(
                byDate.size(),
                generated
        );
    }
}