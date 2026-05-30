package com.sharad.platformapi.dto;

public class EtfUniverseDto {

    private String symbol;
    private String name;
    private String category;
    private String benchmark;

    public EtfUniverseDto() {
    }

    public EtfUniverseDto(
            String symbol,
            String name,
            String category,
            String benchmark) {

        this.symbol = symbol;
        this.name = name;
        this.category = category;
        this.benchmark = benchmark;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }
}