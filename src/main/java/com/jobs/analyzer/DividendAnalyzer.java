package com.jobs.analyzer;

import com.entity.PropertyType;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.DIVIDENDS_PER_SHARE;

@Service
public class DividendAnalyzer extends Analyzer {

    public DividendAnalyzer(CompanyPropertyRepository companyPropertyRepository) {
        super(companyPropertyRepository);
    }

    @Override
    public PropertyType getPropertyType() {
        return DIVIDENDS_PER_SHARE;
    }

    @Override
    public int maxYearsOfAnalysis() {
        return 15;
    }

}
