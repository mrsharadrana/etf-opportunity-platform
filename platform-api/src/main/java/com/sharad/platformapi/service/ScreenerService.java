package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScoreDto;
import com.sharad.platformapi.dto.ScreenerResponseDto;
import com.sharad.platformapi.dto.ScreenerRowDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ScreenerService {

    private final EtfUniverseService etfUniverseService;

    private final EtfScoreService etfScoreService;

    public ScreenerService(
            EtfUniverseService etfUniverseService,
            EtfScoreService etfScoreService
    ) {
        this.etfUniverseService = etfUniverseService;
        this.etfScoreService = etfScoreService;
    }

    public ScreenerResponseDto getScreener() {

        List<ScreenerRowDto> rows =
                new ArrayList<>();

        etfUniverseService.getActiveSymbols()
                .forEach(symbol -> {

                    EtfScoreDto score =
                            etfScoreService.calculate(
                                    symbol
                            );

                    rows.add(
                            new ScreenerRowDto(
                                    0,
                                    symbol,
                                    score.totalScore(),
                                    score.probabilityScore(),
                                    score.relativeStrengthScore(),
                                    score.rating()
                            )
                    );
                });

        rows.sort(
                Comparator.comparing(
                        ScreenerRowDto::totalScore
                ).reversed()
        );

        List<ScreenerRowDto> ranked =
                new ArrayList<>();

        int rank = 1;

        for (ScreenerRowDto row : rows) {

            ranked.add(
                    new ScreenerRowDto(
                            rank++,
                            row.symbol(),
                            row.totalScore(),
                            row.probabilityScore(),
                            row.relativeStrengthScore(),
                            row.rating()
                    )
            );
        }

        return new ScreenerResponseDto(
                ranked
        );
    }
}