package com.jobs.companyproperty;

import com.entity.CompanyProperty;
import com.entity.StockData;
import com.jobs.companyproperty.strategy.PeComputingStrategy;
import com.repository.CompanyPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.entity.PropertyType.AVG_5_YEAR_PE;
import static com.entity.PropertyType.EPS;
import static com.entity.PropertyType.LAST_PE;
import static java.util.stream.Collectors.toList;

@Service
public class PECompanyPropertyCreator {
    private CompanyPropertyRepository companyPropertyRepository;

    @Autowired
    public PECompanyPropertyCreator(CompanyPropertyRepository companyPropertyRepository) {
        this.companyPropertyRepository = companyPropertyRepository;
    }

    public Optional<CompanyProperty> create(StockData stockData, PeComputingStrategy priceComputingStrategy) {
        List<CompanyProperty> sortedEps = companyPropertyRepository.findByTickerAndPropertyType(stockData.getTicker(), EPS)
                .stream()
                .filter(companyProperty -> companyProperty.getProperty() != null)
                .sorted((first, second) -> second.getYear() - first.getYear())
                .collect(toList());

        if (sortedEps.isEmpty()) return Optional.empty();

        return priceComputingStrategy.compute(sortedEps, stockData);
    }
}
