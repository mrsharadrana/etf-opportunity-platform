CREATE TABLE etf_universe
(
    id BIGSERIAL PRIMARY KEY,

    symbol VARCHAR(30) NOT NULL UNIQUE,

    name VARCHAR(100) NOT NULL,

    category VARCHAR(50) NOT NULL,

    benchmark VARCHAR(100),

    enabled BOOLEAN NOT NULL DEFAULT TRUE
);