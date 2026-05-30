package com.sharad.platformapi.repository;

import com.sharad.platformapi.entity.MarketIndicatorHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MarketIndicatorHistoryRepository extends JpaRepository<MarketIndicatorHistory, Long> {

    Optional<MarketIndicatorHistory> findByIndicatorNameAndTradeDate(
            String indicatorName,
            LocalDate tradeDate
    );

    @Query("SELECT m FROM MarketIndicatorHistory m WHERE m.indicatorName = :indicatorName ORDER BY m.tradeDate DESC")
    List<MarketIndicatorHistory> findByIndicatorNameOrderByTradeDateDesc(
            @Param("indicatorName") String indicatorName
    );

    @Query("SELECT m FROM MarketIndicatorHistory m WHERE m.indicatorName = :indicatorName AND m.tradeDate = (SELECT MAX(x.tradeDate) FROM MarketIndicatorHistory x WHERE x.indicatorName = :indicatorName)")
    Optional<MarketIndicatorHistory> findLatestByIndicatorName(
            @Param("indicatorName") String indicatorName
    );
}
