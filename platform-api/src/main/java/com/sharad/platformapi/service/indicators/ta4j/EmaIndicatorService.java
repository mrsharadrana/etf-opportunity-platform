package com.sharad.platformapi.service.indicators.ta4j;

import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.averages.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

@Service
public class EmaIndicatorService {

    public double calculateEma(
            BarSeries series,
            int period
    ) {

        if (series.getBarCount() < period) {
            return 0.0;
        }

        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        EMAIndicator ema =
                new EMAIndicator(
                        closePrice,
                        period
                );

        return ema.getValue(
                series.getEndIndex()
        ).doubleValue();
    }
}