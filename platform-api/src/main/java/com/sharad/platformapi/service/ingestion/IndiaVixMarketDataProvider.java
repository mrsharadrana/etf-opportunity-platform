package com.sharad.platformapi.service.ingestion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@Component
public class IndiaVixMarketDataProvider {

    private static final String INDIA_VIX_SYMBOL = "^INDIAVIX";

    private final YahooFinanceClient yahooFinanceClient;
    private final ObjectMapper objectMapper;

    public IndiaVixMarketDataProvider(
            YahooFinanceClient yahooFinanceClient,
            ObjectMapper objectMapper
    ) {
        this.yahooFinanceClient = yahooFinanceClient;
        this.objectMapper = objectMapper;
    }

    public BigDecimal fetchLatestClose() {
        String response = yahooFinanceClient.fetchChart(INDIA_VIX_SYMBOL);
        JsonNode root = parseJson(response);
        JsonNode result = root.path("chart").path("result");

        if (!result.isArray() || result.isEmpty()) {
            throw new IllegalStateException("Yahoo Finance chart result missing for " + INDIA_VIX_SYMBOL);
        }

        JsonNode quote = result.get(0).path("indicators").path("quote");
        if (!quote.isArray() || quote.isEmpty()) {
            throw new IllegalStateException("Yahoo Finance quote data missing for " + INDIA_VIX_SYMBOL);
        }

        JsonNode closeArray = quote.get(0).path("close");
        if (!closeArray.isArray() || closeArray.isEmpty()) {
            throw new IllegalStateException("Yahoo Finance close series missing for " + INDIA_VIX_SYMBOL);
        }

        for (int index = closeArray.size() - 1; index >= 0; index--) {
            JsonNode valueNode = closeArray.get(index);
            if (!valueNode.isNull()) {
                return new BigDecimal(valueNode.asText());
            }
        }

        throw new IllegalStateException("No valid close value found for " + INDIA_VIX_SYMBOL);
    }

    private JsonNode parseJson(String payload) {
        Objects.requireNonNull(payload, "payload must not be null");

        try {
            return objectMapper.readTree(payload);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to parse Yahoo Finance response", e);
        }
    }
}
