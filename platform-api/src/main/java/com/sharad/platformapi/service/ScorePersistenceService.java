package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScoreDto;
import com.sharad.platformapi.dto.ProbabilityResponseDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScorePersistenceService {

    private final ETFPriceHistoryRepository repository;

    private final EtfUniverseService etfUniverseService;

    private final EtfScoreService etfScoreService;

    private final ProbabilityEngineService probabilityEngineService;

    public ScorePersistenceService(
            ETFPriceHistoryRepository repository,
            EtfUniverseService etfUniverseService,
            EtfScoreService etfScoreService,
            ProbabilityEngineService probabilityEngineService
    ) {
        this.repository = repository;
        this.etfUniverseService = etfUniverseService;
        this.etfScoreService = etfScoreService;
        this.probabilityEngineService = probabilityEngineService;
    }

    public void persistScores() {

        List<String> symbols =
                etfUniverseService.getActiveSymbols();

        for (String symbol : symbols) {

            List<ETFPriceHistory> history =
                    repository.findBySymbolOrderByTradeDateAsc(
                            symbol
                    );

            if (history.isEmpty()) {
                continue;
            }

            ETFPriceHistory latest =
                    history.getLast();

            ProbabilityResponseDto probability =
                    probabilityEngineService.analyze(
                            symbol
                    );

            EtfScoreDto score =
                    etfScoreService.calculate(
                            symbol
                    );

            latest.setProbabilityScore(
                    probability.confidence()
            );

            latest.setRelativeStrengthScore(
                    score.relativeStrengthScore()
            );

            latest.setEtfScore(
                    score.totalScore()
            );

            repository.save(
                    latest
            );
        }
    }
}