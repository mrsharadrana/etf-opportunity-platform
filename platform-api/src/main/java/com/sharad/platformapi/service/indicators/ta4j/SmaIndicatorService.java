package com.sharad.platformapi.service.indicators.ta4j;

import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.averages.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

@Service
public class SmaIndicatorService {

    public double calculateSma(
            BarSeries series,
            int period
    ) {

        if (series.getBarCount() < period) {
            return 0.0;
        }

        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        SMAIndicator sma =
                new SMAIndicator(
                        closePrice,
                        period
                );

        return sma.getValue(
                series.getEndIndex()
        ).doubleValue();
    }
}