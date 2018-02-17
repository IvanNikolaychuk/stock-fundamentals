package com.jobs.companyproperty;

import com.entity.CompanyProperty;
import com.entity.PropertyType;
import com.entity.StockData;
import com.jobs.companyproperty.strategy.PeComputingStrategy;
import com.repository.CompanyPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.entity.PropertyType.*;
import static com.jobs.utils.DataUtils.currentYear;
import static java.util.stream.Collectors.toList;

@Service
public class DividendYieldCreator {
    private CompanyPropertyRepository companyPropertyRepository;

    @Autowired
    public DividendYieldCreator(CompanyPropertyRepository companyPropertyRepository) {
        this.companyPropertyRepository = companyPropertyRepository;
    }

    public Optional<CompanyProperty> create(StockData stockData) {
        List<CompanyProperty> sortedDividends = companyPropertyRepository
                .findByTickerAndPropertyType(stockData.getTicker(), DIVIDENDS_PER_SHARE)
                .stream()
                .filter(companyProperty -> companyProperty.getProperty() != null)
                .sorted((first, second) -> second.getYear() - first.getYear())
                .collect(toList());

        if (sortedDividends.isEmpty()) return Optional.empty();

        final double yield = sortedDividends.get(0).getProperty() / stockData.getClose() * 100 ;

        return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), DIVIDEND_YIELD, yield));
    }

}
