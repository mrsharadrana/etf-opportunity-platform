package com.sharad.platformapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RankingDto {

    private String symbol;

    private LocalDate tradeDate;

    private BigDecimal closePrice;

    private BigDecimal momentumScore;

    private Integer rank;

    private String signal;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getMomentumScore() {
        return momentumScore;
    }

    public void setMomentumScore(BigDecimal momentumScore) {
        this.momentumScore = momentumScore;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }
}