package com.sharad.platformapi.repository;

import com.sharad.platformapi.entity.PortfolioHolding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioHoldingRepository
        extends JpaRepository<
        PortfolioHolding,
        Long
        > {

    List<PortfolioHolding> findAllBySymbol(
            String symbol
    );
}