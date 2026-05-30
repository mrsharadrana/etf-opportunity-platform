package com.sharad.platformapi.service.indicators.ta4j;

import org.springframework.stereotype.Service;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.averages.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

@Service
public class MacdIndicatorService {

    public MacdResult calculateMacd(
            BarSeries series
    ) {

        if (series.getBarCount() < 35) {

            return new MacdResult(
                    0.0,
                    0.0,
                    0.0
            );
        }

        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        MACDIndicator macd =
                new MACDIndicator(
                        closePrice,
                        12,
                        26
                );

        EMAIndicator signalLine =
                new EMAIndicator(
                        macd,
                        9
                );

        double macdValue =
                macd.getValue(
                        series.getEndIndex()
                ).doubleValue();

        double signalValue =
                signalLine.getValue(
                        series.getEndIndex()
                ).doubleValue();

        double histogram =
                macdValue - signalValue;

        return new MacdResult(
                macdValue,
                signalValue,
                histogram
        );
    }

    public record MacdResult(
            Double macd,
            Double signal,
            Double histogram
    ) {
    }
}