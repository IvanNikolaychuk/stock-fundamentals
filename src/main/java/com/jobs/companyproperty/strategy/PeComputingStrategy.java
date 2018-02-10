package com.jobs.companyproperty.strategy;

import com.entity.CompanyProperty;
import com.entity.StockData;

import java.util.List;
import java.util.Optional;

public interface PeComputingStrategy {
    Optional<CompanyProperty> compute(List<CompanyProperty> sortedNotNullEps, StockData stockData);
}
