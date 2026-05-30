package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScoreDto;
import com.sharad.platformapi.dto.PortfolioAllocationDto;
import com.sharad.platformapi.dto.PortfolioResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioAllocatorService {

    private final EtfUniverseService etfUniverseService;

    private final EtfScoreService etfScoreService;

    public PortfolioAllocatorService(
            EtfUniverseService etfUniverseService,
            EtfScoreService etfScoreService
    ) {
        this.etfUniverseService = etfUniverseService;
        this.etfScoreService = etfScoreService;
    }

    public PortfolioResponseDto allocate() {

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

            allocations.add(
                    new PortfolioAllocationDto(
                            score.symbol(),
                            score.totalScore(),
                            allocationPct
                    )
            );
        }

        return new PortfolioResponseDto(
                totalScore,
                allocations
        );
    }
}