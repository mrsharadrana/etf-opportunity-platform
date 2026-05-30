package com.sharad.platformapi.repository;

import com.sharad.platformapi.entity.ETFPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ETFPriceHistoryRepository
        extends JpaRepository<ETFPriceHistory, Long> {

    @Query("""
            SELECT e
            FROM ETFPriceHistory e
            WHERE e.tradeDate = (
                SELECT MAX(x.tradeDate)
                FROM ETFPriceHistory x
            )
            ORDER BY e.rank ASC
            """)
    List<ETFPriceHistory> findLatestRankings();

    List<ETFPriceHistory>
    findBySymbolOrderByTradeDateDesc(
            String symbol
    );

    List<ETFPriceHistory>
    findByTradeDate(
            LocalDate tradeDate
    );

    List<ETFPriceHistory>
    findByTradeDateOrderByRankAsc(
            LocalDate tradeDate
    );

    ETFPriceHistory
    findBySymbolAndTradeDate(
            String symbol,
            LocalDate tradeDate
    );

    boolean existsBySymbolAndTradeDate(
            String symbol,
            LocalDate tradeDate
    );

    List<ETFPriceHistory>
    findBySymbolOrderByTradeDateAsc(
            String symbol
    );

    List<ETFPriceHistory>
    findTop30BySymbolOrderByTradeDateDesc(
            String symbol
    );
}