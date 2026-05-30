package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.SignalHistoryDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.entity.MarketRegime;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.repository.MarketRegimeRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignalHistoryService {

    private final MarketRegimeRepository marketRegimeRepository;

    private final ETFPriceHistoryRepository etfRepository;

    public SignalHistoryService(
            MarketRegimeRepository marketRegimeRepository,
            ETFPriceHistoryRepository etfRepository
    ) {
        this.marketRegimeRepository = marketRegimeRepository;
        this.etfRepository = etfRepository;
    }

    public List<SignalHistoryDto> generateSignals() {

        List<SignalHistoryDto> signals =
                new ArrayList<>();

        List<MarketRegime> regimes =
                marketRegimeRepository
                        .findAllByOrderByTradeDateAsc();

        for (MarketRegime regime : regimes) {

            List<ETFPriceHistory> rankings =
                    etfRepository
                            .findByTradeDateOrderByRankAsc(
                                    regime.getTradeDate()
                            );

            if (rankings.isEmpty()) {
                continue;
            }

            String selectedEtf;

            if ("RISK_ON".equalsIgnoreCase(
                    regime.getMarketRegime()
            )) {

                selectedEtf =
                        rankings.getFirst()
                                .getSymbol();

            } else {

                selectedEtf =
                        "GOLDBEES.NS";
            }

            signals.add(
                    new SignalHistoryDto(
                            regime.getTradeDate(),
                            selectedEtf,
                            regime.getMarketRegime()
                    )
            );
        }

        return signals;
    }
}