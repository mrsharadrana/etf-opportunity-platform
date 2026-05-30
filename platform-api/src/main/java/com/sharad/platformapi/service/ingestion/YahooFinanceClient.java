package com.sharad.platformapi.service.ingestion;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
public class YahooFinanceClient {

    private static final String BASE_URL = "https://query1.finance.yahoo.com";

    private final WebClient webClient;

    public YahooFinanceClient() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public String fetchChart(String symbol) {
        Objects.requireNonNull(symbol, "symbol must not be null");

        String response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v8/finance/chart/{symbol}")
                .queryParam("range", "5d")
                .queryParam("interval", "1d")
                .build(symbol)
            )
            .retrieve()
            .bodyToMono(String.class)
            .block();

        if (response == null || response.isBlank()) {
            throw new IllegalStateException(
                    "Yahoo Finance returned no response for symbol: " + symbol
            );
        }

        return response;
    }
}
