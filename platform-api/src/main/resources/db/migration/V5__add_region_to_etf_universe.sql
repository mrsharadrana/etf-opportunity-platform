ALTER TABLE etf_universe
ADD COLUMN region VARCHAR(50);

UPDATE etf_universe
SET region = 'INDIA'
WHERE symbol IN (
    'NIFTYBEES.NS',
    'BANKBEES.NS',
    'NEXT50.NS',
    'HDFCSML250.NS'
);

UPDATE etf_universe
SET region = 'USA'
WHERE symbol IN (
    'MON100.NS',
    'MAFANG.NS'
);

UPDATE etf_universe
SET region = 'CHINA'
WHERE symbol IN (
    'HNGSNGBEES.NS'
);

UPDATE etf_universe
SET region = 'GLOBAL'
WHERE symbol IN (
    'GOLDBEES.NS',
    'SILVERBEES.NS'
);