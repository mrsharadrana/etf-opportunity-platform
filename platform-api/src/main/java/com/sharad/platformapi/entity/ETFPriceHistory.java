package com.sharad.platformapi.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "etf_price_history")
public class ETFPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    @Column(name = "trade_date")
    private LocalDate tradeDate;

    @Column(name = "open_price")
    private BigDecimal openPrice;

    @Column(name = "high_price")
    private BigDecimal highPrice;

    @Column(name = "low_price")
    private BigDecimal lowPrice;

    @Column(name = "close_price")
    private BigDecimal closePrice;

    private Long volume;

    @Column(name = "sma_20")
    private BigDecimal sma20;

    @Column(name = "sma_50")
    private BigDecimal sma50;

    @Column(name = "sma_200")
    private BigDecimal sma200;

    @Column(name = "returns_1m")
    private BigDecimal returns1m;

    @Column(name = "returns_3m")
    private BigDecimal returns3m;

    @Column(name = "returns_6m")
    private BigDecimal returns6m;

    @Column(name = "momentum_score")
    private BigDecimal momentumScore;

    private Integer rank;

    private String signal;

    @Column(name = "relative_strength_score")
    private Integer relativeStrengthScore;

    @Column(name = "probability_score")
    private Integer probabilityScore;

    @Column(name = "etf_score")
    private Integer etfScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public BigDecimal getSma20() {
        return sma20;
    }

    public void setSma20(BigDecimal sma20) {
        this.sma20 = sma20;
    }

    public BigDecimal getSma50() {
        return sma50;
    }

    public void setSma50(BigDecimal sma50) {
        this.sma50 = sma50;
    }

    public BigDecimal getSma200() {
        return sma200;
    }

    public void setSma200(BigDecimal sma200) {
        this.sma200 = sma200;
    }

    public BigDecimal getReturns1m() {
        return returns1m;
    }

    public void setReturns1m(BigDecimal returns1m) {
        this.returns1m = returns1m;
    }

    public BigDecimal getReturns3m() {
        return returns3m;
    }

    public void setReturns3m(BigDecimal returns3m) {
        this.returns3m = returns3m;
    }

    public BigDecimal getReturns6m() {
        return returns6m;
    }

    public void setReturns6m(BigDecimal returns6m) {
        this.returns6m = returns6m;
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

    public Integer getRelativeStrengthScore() {
        return relativeStrengthScore;
    }

    public void setRelativeStrengthScore(Integer relativeStrengthScore) {
        this.relativeStrengthScore = relativeStrengthScore;
    }

    public Integer getProbabilityScore() {
        return probabilityScore;
    }

    public void setProbabilityScore(Integer probabilityScore) {
        this.probabilityScore = probabilityScore;
    }

    public Integer getEtfScore() {
        return etfScore;
    }

    public void setEtfScore(Integer etfScore) {
        this.etfScore = etfScore;
    }
}