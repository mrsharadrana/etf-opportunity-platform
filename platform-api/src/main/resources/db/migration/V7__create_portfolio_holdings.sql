CREATE TABLE portfolio_holdings
(
    id BIGSERIAL PRIMARY KEY,

    symbol VARCHAR(50) NOT NULL,

    quantity NUMERIC(19,4) NOT NULL,

    entry_price NUMERIC(19,4) NOT NULL,

    entry_date DATE NOT NULL,

    highest_price_since_entry NUMERIC(19,4) NOT NULL,

    trailing_stop_loss NUMERIC(19,4) NOT NULL,

    current_state VARCHAR(30) NOT NULL
);