package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.RelativeStrengthDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RelativeStrengthService {

    private final ETFPriceHistoryRepository repository;

    public RelativeStrengthService(
            ETFPriceHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public List<RelativeStrengthDto> calculate() {

        List<ETFPriceHistory> rankings =
                repository.findLatestRankings();

        rankings.sort(
                Comparator.comparing(
                        ETFPriceHistory::getMomentumScore
                ).reversed()
        );

        int total =
                rankings.size();

        List<RelativeStrengthDto> result =
                new ArrayList<>();

        for (int i = 0; i < rankings.size(); i++) {

            ETFPriceHistory etf =
                    rankings.get(i);

            int rank =
                    i + 1;

            int rsScore;

            if (total == 1) {

                rsScore = 100;

            } else {

                rsScore =
                        (int) Math.round(
                                ((double) (total - rank)
                                        / (total - 1))
                                        * 100
                        );
            }

            result.add(
                    new RelativeStrengthDto(
                            etf.getSymbol(),
                            etf.getMomentumScore(),
                            rank,
                            rsScore
                    )
            );
        }

        return result;
    }
}