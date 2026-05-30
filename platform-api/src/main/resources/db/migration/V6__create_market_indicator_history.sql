CREATE TABLE market_indicator_history (
    id BIGSERIAL PRIMARY KEY,
    trade_date DATE NOT NULL,
    indicator_name VARCHAR(100) NOT NULL,
    indicator_value NUMERIC(18, 6) NOT NULL,
    source VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uq_market_indicator_date_name UNIQUE (trade_date, indicator_name)
);

CREATE INDEX idx_market_indicator_name_date
    ON market_indicator_history (indicator_name, trade_date);
