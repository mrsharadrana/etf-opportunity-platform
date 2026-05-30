package com.sharad.platformapi.repository;

import com.sharad.platformapi.entity.MarketRegime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MarketRegimeRepository
        extends JpaRepository<MarketRegime, LocalDate> {

    @Query("""
            SELECT m
            FROM MarketRegime m
            WHERE m.tradeDate = (
                SELECT MAX(x.tradeDate)
                FROM MarketRegime x
            )
            """)
    MarketRegime findLatestRegime();

    List<MarketRegime>
    findTop30ByOrderByTradeDateDesc();

    List<MarketRegime>
    findAllByOrderByTradeDateAsc();
}