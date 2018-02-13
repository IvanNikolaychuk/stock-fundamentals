package com.jobs.analyzer.balancesheet;

import com.entity.PropertyType;
import com.jobs.analyzer.Analyzer;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.TOTAL_DEBT_TO_EQUITY;

@Service
public class TotalDebtToEquityAnalyzer extends Analyzer {
    @Override
    public PropertyType getPropertyType() {
        return TOTAL_DEBT_TO_EQUITY;
    }
}
