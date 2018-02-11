package com.jobs.companyproperty.strategy;

import com.entity.CompanyProperty;
import com.entity.StockData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.entity.PropertyType.LAST_PE;
import static com.jobs.utils.DataUtils.currentYear;

@Component
public class LastEpsStrategy implements PeComputingStrategy {

    @Override
    public Optional<CompanyProperty> compute(List<CompanyProperty> sortedNotNullEps, StockData stockData) {
        if (sortedNotNullEps.get(0).getProperty() == 0d)
            return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), LAST_PE, 0d));

        final double pe = stockData.getClose() / sortedNotNullEps.get(0).getProperty();
        System.out.println(stockData.getTicker());
        return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), LAST_PE, pe));
    }
}
