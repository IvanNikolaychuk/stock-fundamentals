package com.jobs.analyzer;

import com.entity.PropertyType;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.NUMBER_OF_SHARES;

@Service
public class NumberOfSharesAnalyzer extends Analyzer {
    public NumberOfSharesAnalyzer(CompanyPropertyRepository companyPropertyRepository) {
        super(companyPropertyRepository);
    }

    @Override
    public PropertyType getPropertyType() {
        return NUMBER_OF_SHARES;
    }

    @Override
    public int maxYearsOfAnalysis() {
        return 10;
    }
}
