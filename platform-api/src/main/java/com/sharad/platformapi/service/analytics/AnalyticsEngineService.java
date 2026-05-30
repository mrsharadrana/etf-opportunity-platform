package com.sharad.platformapi.service.analytics;

import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.service.EtfUniverseService;
import com.sharad.platformapi.service.indicators.IndicatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class AnalyticsEngineService {

    private final ETFPriceHistoryRepository repository;

    private final IndicatorService indicatorService;

    private final EtfUniverseService etfUniverseService;

    public AnalyticsEngineService(
            ETFPriceHistoryRepository repository,
            IndicatorService indicatorService,
            EtfUniverseService etfUniverseService
    ) {
        this.repository = repository;
        this.indicatorService = indicatorService;
        this.etfUniverseService = etfUniverseService;
    }

    public void generateRankings() {

        List<String> symbols =
                etfUniverseService.getActiveSymbols();

        for (String symbol : symbols) {

            List<ETFPriceHistory> history =
                    repository.findBySymbolOrderByTradeDateAsc(
                            symbol
                    );

            for (int i = 0; i < history.size(); i++) {

                ETFPriceHistory current =
                        history.get(i);

                if (i >= 21) {

                    BigDecimal returns1m =
                            indicatorService.calculateReturns(
                                    current.getClosePrice(),
                                    history.get(i - 21)
                                            .getClosePrice()
                            );

                    current.setReturns1m(
                            returns1m
                    );
                }

                if (i >= 63) {

                    BigDecimal returns3m =
                            indicatorService.calculateReturns(
                                    current.getClosePrice(),
                                    history.get(i - 63)
                                            .getClosePrice()
                            );

                    current.setReturns3m(
                            returns3m
                    );
                }

                if (i >= 126) {

                    BigDecimal returns6m =
                            indicatorService.calculateReturns(
                                    current.getClosePrice(),
                                    history.get(i - 126)
                                            .getClosePrice()
                            );

                    current.setReturns6m(
                            returns6m
                    );
                }

                BigDecimal momentumScore =
                        indicatorService.calculateMomentumScore(

                                current.getReturns1m() != null
                                        ? current.getReturns1m()
                                        : BigDecimal.ZERO,

                                current.getReturns3m() != null
                                        ? current.getReturns3m()
                                        : BigDecimal.ZERO,

                                current.getReturns6m() != null
                                        ? current.getReturns6m()
                                        : BigDecimal.ZERO
                        );

                current.setMomentumScore(
                        momentumScore
                );

                current.setSignal(
                        indicatorService.generateSignal(
                                momentumScore
                        )
                );

                repository.save(current);
            }
        }

        generateRanks();
    }

    private void generateRanks() {

        List<ETFPriceHistory> rankings =
                repository.findLatestRankings();

        rankings.sort(
                Comparator.comparing(
                        ETFPriceHistory::getMomentumScore
                ).reversed()
        );

        int rank = 1;

        for (ETFPriceHistory etf : rankings) {

            etf.setRank(rank++);

            repository.save(etf);
        }
    }
}