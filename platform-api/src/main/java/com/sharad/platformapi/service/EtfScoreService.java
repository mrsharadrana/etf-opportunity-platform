package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScoreDto;
import com.sharad.platformapi.dto.ProbabilityResponseDto;
import com.sharad.platformapi.dto.RelativeStrengthDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtfScoreService {

    private final ProbabilityEngineService probabilityEngineService;

    private final RelativeStrengthService relativeStrengthService;

    public EtfScoreService(
            ProbabilityEngineService probabilityEngineService,
            RelativeStrengthService relativeStrengthService
    ) {
        this.probabilityEngineService = probabilityEngineService;
        this.relativeStrengthService = relativeStrengthService;
    }

    public EtfScoreDto calculate(
            String symbol
    ) {

        ProbabilityResponseDto probability =
                probabilityEngineService.analyze(
                        symbol
                );

        List<RelativeStrengthDto> rsList =
                relativeStrengthService.calculate();

        RelativeStrengthDto rs =
                rsList.stream()
                        .filter(x ->
                                x.symbol().equals(symbol))
                        .findFirst()
                        .orElse(
                                new RelativeStrengthDto(
                                        symbol,
                                        java.math.BigDecimal.ZERO,
                                        999,
                                        0
                                )
                        );

        int score =
                (int) Math.round(
                        probability.confidence() * 0.70
                                + rs.relativeStrengthScore() * 0.30
                );

        String rating;

        if (score >= 80) {

            rating = "STRONG_BUY";

        } else if (score >= 60) {

            rating = "BUY";

        } else if (score >= 40) {

            rating = "HOLD";

        } else {

            rating = "AVOID";
        }

        return new EtfScoreDto(
                symbol,
                probability.confidence(),
                rs.relativeStrengthScore(),
                score,
                rating
        );
    }
}