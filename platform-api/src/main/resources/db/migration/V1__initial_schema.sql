CREATE TABLE IF NOT EXISTS market_regimes (

    trade_date DATE PRIMARY KEY,

    market_regime VARCHAR(50),

    top_ranked_etf VARCHAR(100)
);



CREATE TABLE IF NOT EXISTS etf_price_history (

    id BIGSERIAL PRIMARY KEY,

    symbol VARCHAR(100) NOT NULL,

    trade_date DATE NOT NULL,

    close_price NUMERIC(20,4),

    returns_1m NUMERIC(20,4),

    returns_3m NUMERIC(20,4),

    returns_6m NUMERIC(20,4),

    momentum_score NUMERIC(20,4),

    signal VARCHAR(20),

    rank INTEGER
);



CREATE INDEX IF NOT EXISTS idx_etf_symbol
ON etf_price_history(symbol);



CREATE INDEX IF NOT EXISTS idx_trade_date
ON etf_price_history(trade_date);



CREATE INDEX IF NOT EXISTS idx_symbol_trade_date
ON etf_price_history(symbol, trade_date);