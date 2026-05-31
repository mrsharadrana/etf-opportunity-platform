package com.sharad.platformapi.entity;

import com.sharad.platformapi.domain.PositionState;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "portfolio_holdings")
public class PortfolioHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private BigDecimal quantity;

    private BigDecimal entryPrice;

    private LocalDate entryDate;

    private BigDecimal highestPriceSinceEntry;

    private BigDecimal trailingStopLoss;

    @Enumerated(EnumType.STRING)
    private PositionState currentState;

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(BigDecimal entryPrice) {
        this.entryPrice = entryPrice;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getHighestPriceSinceEntry() {
        return highestPriceSinceEntry;
    }

    public void setHighestPriceSinceEntry(
            BigDecimal highestPriceSinceEntry
    ) {
        this.highestPriceSinceEntry =
                highestPriceSinceEntry;
    }

    public BigDecimal getTrailingStopLoss() {
        return trailingStopLoss;
    }

    public void setTrailingStopLoss(
            BigDecimal trailingStopLoss
    ) {
        this.trailingStopLoss =
                trailingStopLoss;
    }

    public PositionState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(
            PositionState currentState
    ) {
        this.currentState =
                currentState;
    }
}