package com.jobs.analyzer;

import com.entity.PropertyType;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.TOTAL_DEBT_TO_EQUITY;

@Service
public class TotalDebtToEquityAnalyzer extends Analyzer{
    public TotalDebtToEquityAnalyzer(CompanyPropertyRepository companyPropertyRepository) {
        super(companyPropertyRepository);
    }

    @Override
    public PropertyType getPropertyType() {
        return TOTAL_DEBT_TO_EQUITY;
    }
}
