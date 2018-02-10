package com.jobs.companyproperty.strategy;

import com.entity.CompanyProperty;
import com.entity.StockData;
import com.jobs.utils.DataUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.entity.PropertyType.AVG_5_YEAR_PE;
import static com.jobs.utils.DataUtils.currentYear;

@Component
public class Avg5YearEpsStrategy implements PeComputingStrategy {

    @Override
    public Optional<CompanyProperty> compute(List<CompanyProperty> sortedNotNullEps, StockData stockData) {
        if (sortedNotNullEps.size() < 5) return Optional.empty();

        Double avgEps = sortedNotNullEps.subList(0, 5).stream()
                .mapToInt(CompanyProperty::getPropertyAsInt)
                .average()
                .getAsDouble();

        final BigDecimal avgPe = stockData.getClose().divide(new BigDecimal(avgEps));
        return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), AVG_5_YEAR_PE, avgPe.toString()));
    }
}
