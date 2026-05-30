package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScorecardDto;
import com.sharad.platformapi.dto.ProbabilityResponseDto;

import org.springframework.stereotype.Service;

@Service
public class ProbabilityEngineService {

    private final ScorecardService scorecardService;

    public ProbabilityEngineService(
            ScorecardService scorecardService
    ) {
        this.scorecardService = scorecardService;
    }

    public ProbabilityResponseDto analyze(
            String symbol
    ) {

        EtfScorecardDto scorecard =
                scorecardService.getScorecard(
                        symbol
                );

        int confidence = 0;

        int bullish = 0;

        int bearish = 0;

        /*
         * Evidence-Based Weighting V2
         *
         * Trend (Price > SMA200)      = 35
         * MACD Histogram > 0          = 25
         * RSI > 50                    = 15
         * Momentum > 20               = 15
         * SMA50 > SMA200              = 10
         */

        if (scorecard.above200DMA()) {

            confidence += 35;
            bullish++;

        } else {

            bearish++;
        }

        if (scorecard.macdHistogram() > 0) {

            confidence += 25;
            bullish++;

        } else {

            bearish++;
        }

        if (scorecard.rsi() > 50) {

            confidence += 15;
            bullish++;

        } else {

            bearish++;
        }

        if (scorecard.momentumScore() > 20) {

            confidence += 15;
            bullish++;

        } else {

            bearish++;
        }

        if (scorecard.sma50() > scorecard.sma200()) {

            confidence += 10;
            bullish++;

        } else {

            bearish++;
        }

        String rating;

        if (confidence >= 80) {

            rating = "STRONG_BUY";

        } else if (confidence >= 60) {

            rating = "BUY";

        } else if (confidence >= 40) {

            rating = "HOLD";

        } else {

            rating = "AVOID";
        }

        return new ProbabilityResponseDto(
                symbol,
                confidence,
                bullish,
                bearish,
                rating
        );
    }
}