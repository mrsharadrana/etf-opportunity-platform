ALTER TABLE etf_price_history
ADD COLUMN relative_strength_score INTEGER;

ALTER TABLE etf_price_history
ADD COLUMN probability_score INTEGER;

ALTER TABLE etf_price_history
ADD COLUMN etf_score INTEGER;