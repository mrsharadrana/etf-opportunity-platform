package com.sharad.platformapi.exception;

public class InvalidEtfSymbolException
        extends RuntimeException {

    public InvalidEtfSymbolException(
            String symbol
    ) {
        super(
                "ETF symbol not found in ETF Universe: "
                        + symbol
        );
    }
}