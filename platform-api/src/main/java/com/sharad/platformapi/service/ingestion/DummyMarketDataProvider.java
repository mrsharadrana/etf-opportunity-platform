package com.sharad.platformapi.service.ingestion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharad.platformapi.dto.HistoricalPriceDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class DummyMarketDataProvider
        implements MarketDataProvider {

    private final WebClient webClient;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public DummyMarketDataProvider() {

        this.webClient = WebClient.builder()
                .baseUrl("https://query1.finance.yahoo.com")
                .build();
    }

    @Override
    public List<HistoricalPriceDto> getHistory(
            String symbol
    ) {

        try {

            String response =
                    webClient.get()
                            .uri(
                                    "/v8/finance/chart/"
                                            + symbol
                                            + "?range=1y&interval=1d"
                            )
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            JsonNode root =
                    objectMapper.readTree(response);

            JsonNode result =
                    root.path("chart")
                            .path("result")
                            .get(0);

            JsonNode timestamps =
                    result.path("timestamp");

            JsonNode quote =
                    result.path("indicators")
                            .path("quote")
                            .get(0);

            JsonNode opens =
                    quote.path("open");

            JsonNode highs =
                    quote.path("high");

            JsonNode lows =
                    quote.path("low");

            JsonNode closes =
                    quote.path("close");

            JsonNode volumes =
                    quote.path("volume");

            List<HistoricalPriceDto> history =
                    new ArrayList<>();

            for (int i = 0;
                 i < timestamps.size();
                 i++) {

                if (closes.get(i).isNull()) {
                    continue;
                }

                LocalDate tradeDate =
                        Instant.ofEpochSecond(
                                        timestamps.get(i)
                                                .asLong()
                                )
                                .atZone(
                                        ZoneId.systemDefault()
                                )
                                .toLocalDate();

                history.add(
                        new HistoricalPriceDto(
                                tradeDate,
                                BigDecimal.valueOf(
                                        opens.get(i).asDouble()
                                ),
                                BigDecimal.valueOf(
                                        highs.get(i).asDouble()
                                ),
                                BigDecimal.valueOf(
                                        lows.get(i).asDouble()
                                ),
                                BigDecimal.valueOf(
                                        closes.get(i).asDouble()
                                ),
                                volumes.get(i).asLong()
                        )
                );
            }

            return history;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed loading data for "
                            + symbol,
                    e
            );
        }
    }
}