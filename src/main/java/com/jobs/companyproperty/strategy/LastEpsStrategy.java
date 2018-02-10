package com.jobs.companyproperty.strategy;

import com.entity.CompanyProperty;
import com.entity.StockData;
import com.jobs.utils.DataUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.entity.PropertyType.LAST_PE;
import static com.jobs.utils.DataUtils.currentYear;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Component
public class LastEpsStrategy implements PeComputingStrategy {

    @Override
    public Optional<CompanyProperty> compute(List<CompanyProperty> sortedNotNullEps, StockData stockData) {
        if (sortedNotNullEps.get(0).getProperty().doubleValue() == 0d)
            return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), LAST_PE, "0"));

        final BigDecimal pe = stockData.getClose().divide(sortedNotNullEps.get(0).getProperty(), ROUND_HALF_UP);

        return Optional.of(new CompanyProperty(stockData.getTicker(), currentYear(), LAST_PE, pe.toString()));
    }
}
