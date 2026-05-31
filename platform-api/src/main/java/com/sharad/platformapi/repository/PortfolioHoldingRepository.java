package com.sharad.platformapi.repository;

import com.sharad.platformapi.entity.PortfolioHolding;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioHoldingRepository
        extends JpaRepository<
        PortfolioHolding,
        Long
        > {
}