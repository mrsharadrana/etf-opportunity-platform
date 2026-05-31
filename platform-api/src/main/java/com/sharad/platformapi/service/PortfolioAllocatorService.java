package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScoreDto;
import com.sharad.platformapi.dto.MarketRegimeDto;
import com.sharad.platformapi.dto.PortfolioAllocationDto;
import com.sharad.platformapi.dto.PortfolioResponseDto;
import com.sharad.platformapi.service.regime.MarketRegimeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioAllocatorService {

    private static final BigDecimal DEFAULT_CAPITAL =
            BigDecimal.valueOf(100000);

    private final EtfUniverseService etfUniverseService;

    private final EtfScoreService etfScoreService;

    private final MarketRegimeService marketRegimeService;

    public PortfolioAllocatorService(
            EtfUniverseService etfUniverseService,
            EtfScoreService etfScoreService,
            MarketRegimeService marketRegimeService
    ) {
        this.etfUniverseService = etfUniverseService;
        this.etfScoreService = etfScoreService;
        this.marketRegimeService = marketRegimeService;
    }

    public PortfolioResponseDto allocate() {

        MarketRegimeDto regime =
                marketRegimeService.getLatestRegime();

        BigDecimal investedPct =
                determineInvestedPct(
                        regime.getMarketRegime()
                );

        BigDecimal investedCapital =
                DEFAULT_CAPITAL.multiply(
                        investedPct
                );

        BigDecimal cashReserve =
                DEFAULT_CAPITAL.subtract(
                        investedCapital
                );

        List<EtfScoreDto> eligibleEtfs =
                new ArrayList<>();

        for (String symbol :
                etfUniverseService.getActiveSymbols()) {

            try {

                EtfScoreDto score =
                        etfScoreService.calculate(
                                symbol
                        );

                if (score.totalScore() >= 60) {

                    eligibleEtfs.add(
                            score
                    );
                }

            } catch (Exception ex) {

                System.out.println(
                        "Skipping ETF: "
                                + symbol
                                + " - "
                                + ex.getMessage()
                );
            }
        }

        int totalScore =
                eligibleEtfs.stream()
                        .mapToInt(
                                EtfScoreDto::totalScore
                        )
                        .sum();

        List<PortfolioAllocationDto> allocations =
                new ArrayList<>();

        for (EtfScoreDto score : eligibleEtfs) {

            int allocationPct =
                    (int) Math.round(
                            (score.totalScore() * 100.0)
                                    / totalScore
                    );

            BigDecimal allocationAmount =
                    investedCapital.multiply(
                                    BigDecimal.valueOf(
                                            allocationPct
                                    )
                            )
                            .divide(
                                    BigDecimal.valueOf(100),
                                    2,
                                    RoundingMode.HALF_UP
                            );

            allocations.add(
                    new PortfolioAllocationDto(
                            score.symbol(),
                            score.totalScore(),
                            allocationPct,
                            allocationAmount
                    )
            );
        }

        return new PortfolioResponseDto(
                DEFAULT_CAPITAL,
                investedCapital,
                cashReserve,
                regime.getMarketRegime(),
                totalScore,
                allocations
        );
    }

    private BigDecimal determineInvestedPct(
            String marketRegime
    ) {

        if ("RISK_OFF".equalsIgnoreCase(
                marketRegime
        )) {
            return BigDecimal.valueOf(0.70);
        }

        if ("SIDEWAYS".equalsIgnoreCase(
                marketRegime
        )) {
            return BigDecimal.valueOf(0.90);
        }

        return BigDecimal.ONE;
    }
}