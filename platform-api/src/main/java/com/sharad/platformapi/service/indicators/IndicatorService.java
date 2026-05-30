package com.sharad.platformapi.service.indicators;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class IndicatorService {

    public BigDecimal calculateSMA(
            List<BigDecimal> prices
    ) {

        if (prices == null || prices.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (BigDecimal price : prices) {
            total = total.add(price);
        }

        return total.divide(
                BigDecimal.valueOf(prices.size()),
                4,
                RoundingMode.HALF_UP
        );
    }


    public BigDecimal calculateReturns(
            BigDecimal currentPrice,
            BigDecimal oldPrice
    ) {

        if (oldPrice == null ||
                oldPrice.compareTo(BigDecimal.ZERO) == 0) {

            return BigDecimal.ZERO;
        }

        return currentPrice.subtract(oldPrice)
                .divide(
                        oldPrice,
                        4,
                        RoundingMode.HALF_UP
                )
                .multiply(BigDecimal.valueOf(100));
    }


    public BigDecimal calculateMomentumScore(
            BigDecimal returns1m,
            BigDecimal returns3m,
            BigDecimal returns6m
    ) {

        returns1m =
                returns1m != null
                        ? returns1m
                        : BigDecimal.ZERO;

        returns3m =
                returns3m != null
                        ? returns3m
                        : BigDecimal.ZERO;

        returns6m =
                returns6m != null
                        ? returns6m
                        : BigDecimal.ZERO;

        return returns1m
                .multiply(BigDecimal.valueOf(0.3))
                .add(
                        returns3m.multiply(
                                BigDecimal.valueOf(0.3)
                        )
                )
                .add(
                        returns6m.multiply(
                                BigDecimal.valueOf(0.4)
                        )
                )
                .setScale(
                        4,
                        RoundingMode.HALF_UP
                );
    }


    public String generateSignal(
            BigDecimal momentumScore
    ) {

        if (momentumScore.compareTo(
                BigDecimal.valueOf(10)
        ) > 0) {

            return "BUY";
        }

        if (momentumScore.compareTo(
                BigDecimal.ZERO
        ) > 0) {

            return "HOLD";
        }

        return "AVOID";
    }
}