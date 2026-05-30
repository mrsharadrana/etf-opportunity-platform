package com.sharad.platformapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "market_indicator_history")
public class MarketIndicatorHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "indicator_name", nullable = false, length = 100)
    private String indicatorName;

    @Column(name = "indicator_value", nullable = false, precision = 18, scale = 6)
    private BigDecimal indicatorValue;

    @Column(name = "source", nullable = false, length = 100)
    private String source;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public MarketIndicatorHistory() {
    }

    public MarketIndicatorHistory(
            LocalDate tradeDate,
            String indicatorName,
            BigDecimal indicatorValue,
            String source,
            LocalDateTime createdAt
    ) {
        this.tradeDate = tradeDate;
        this.indicatorName = indicatorName;
        this.indicatorValue = indicatorValue;
        this.source = source;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public BigDecimal getIndicatorValue() {
        return indicatorValue;
    }

    public void setIndicatorValue(BigDecimal indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
