package com.jobs.companyproperty.strategy;

import com.entity.CompanyProperty;
import com.entity.StockData;
import com.jobs.utils.DataUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.entity.PropertyType.AVG_5_YEAR_PE;
import static com.entity.PropertyType.LAST_PE;
import static com.jobs.utils.DataUtils.currentYear;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Component
public class Avg5YearEpsStrategy implements PeComputingStrategy {

    @Override
    public Optional<CompanyProperty> compute(List<CompanyProperty> sortedNotNullEps, StockData stockData) {
        if (sortedNotNullEps.size() < 5) return Optional.empty();

        double sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += sortedNotNullEps.get(i).getProperty();
        }
        Double avgEps = sum / 5;

        if (avgEps == 0d)
            return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), AVG_5_YEAR_PE, 0d));

        final double avgPe = stockData.getClose() / avgEps;
        return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), AVG_5_YEAR_PE, avgPe));
    }
}
