package com.jobs.analyzer.incomestatement;

import com.entity.PropertyType;
import com.jobs.analyzer.Analyzer;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.DIVIDENDS_PER_SHARE;

@Service
public class DividendAnalyzer extends Analyzer {
    @Override
    public PropertyType getPropertyType() {
        return DIVIDENDS_PER_SHARE;
    }

    @Override
    public int maxYearsOfAnalysis() {
        return 15;
    }

}
