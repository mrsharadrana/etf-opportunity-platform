package com.sharad.platformapi.service.indicators.ta4j;

import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

@Service
public class RsiIndicatorService {

    public double calculateRsi(
            BarSeries series
    ) {

        if (series == null || series.getBarCount() < 15) {
            return 0.0;
        }

        ClosePriceIndicator closePriceIndicator =
                new ClosePriceIndicator(series);

        RSIIndicator rsiIndicator =
                new RSIIndicator(
                        closePriceIndicator,
                        14
                );

        return rsiIndicator
                .getValue(series.getEndIndex())
                .doubleValue();
    }
}