package com.sharad.platformapi.service.indicators.ta4j;

import com.sharad.platformapi.entity.ETFPriceHistory;

import org.springframework.stereotype.Service;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class Ta4jSeriesBuilderService {

    public BarSeries buildSeries(
            List<ETFPriceHistory> history
    ) {

        BarSeries series =
                new BaseBarSeriesBuilder()
                        .withName("ETF-Series")
                        .build();

        for (ETFPriceHistory row : history) {

            double price =
                    row.getClosePrice()
                            .doubleValue();

            Instant endTime =
                    row.getTradeDate()
                            .atStartOfDay()
                            .toInstant(ZoneOffset.UTC);

            series.barBuilder()
                    .timePeriod(Duration.ofDays(1))
                    .endTime(endTime)
                    .openPrice(price)
                    .highPrice(price)
                    .lowPrice(price)
                    .closePrice(price)
                    .volume(1)
                    .add();
        }

        return series;
    }
}