package com.sharad.platformapi.service;

import com.sharad.platformapi.domain.SignalStrength;
import com.sharad.platformapi.dto.RankingDto;
import com.sharad.platformapi.dto.SignalStrengthDto;
import com.sharad.platformapi.service.ranking.RankingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignalStrengthService {

    private final RankingService rankingService;

    public SignalStrengthService(
            RankingService rankingService
    ) {
        this.rankingService = rankingService;
    }

    public List<SignalStrengthDto> generateSignals() {

        return rankingService.getLatestRankings()
                .stream()
                .map(this::mapSignal)
                .toList();
    }

    private SignalStrengthDto mapSignal(
            RankingDto ranking
    ) {

        SignalStrength signal;
        String reason;

        if (
                ranking.getRank() <= 2
                        && "BUY".equalsIgnoreCase(
                        ranking.getSignal()
                )
        ) {

            signal = SignalStrength.STRONG_BUY;
            reason = "Top ranked ETF with strong momentum.";

        } else if (
                ranking.getRank() <= 5
                        && "BUY".equalsIgnoreCase(
                        ranking.getSignal()
                )
        ) {

            signal = SignalStrength.BUY;
            reason = "Positive momentum and ranked in top opportunities.";

        } else if (
                ranking.getRank() == 6
        ) {

            signal = SignalStrength.HOLD;
            reason = "Neutral position. Monitor closely.";

        } else if (
                ranking.getRank() == 7
        ) {

            signal = SignalStrength.REDUCE_20;
            reason = "Weak ranking. Reduce exposure by 20%.";

        } else if (
                ranking.getRank() == 8
        ) {

            signal = SignalStrength.REDUCE_40;
            reason = "Poor ranking. Reduce exposure by 40%.";

        } else {

            signal = SignalStrength.EXIT;
            reason = "Bottom ranked ETF. Exit position.";

        }

        return new SignalStrengthDto(
                ranking.getSymbol(),
                ranking.getRank(),
                ranking.getMomentumScore(),
                signal.name(),
                reason
        );
    }
}