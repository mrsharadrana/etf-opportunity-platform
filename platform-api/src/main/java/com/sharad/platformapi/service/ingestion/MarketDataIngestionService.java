package com.sharad.platformapi.service.ingestion;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MarketDataIngestionService {

    private final WebClient webClient;

    public MarketDataIngestionService() {

        this.webClient = WebClient.builder()
                .baseUrl("https://stooq.com")
                .build();
    }

    public String fetchMarketData(
            String symbol
    ) {

        try {

            return webClient
                    .get()
                    .uri("/q/l/?s=" +
                            symbol +
                            "&f=sd2t2ohlcv&h&e=json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {

            return "Failed to fetch market data";
        }
    }
}