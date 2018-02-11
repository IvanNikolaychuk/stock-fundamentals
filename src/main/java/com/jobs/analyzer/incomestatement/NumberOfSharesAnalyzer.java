package com.jobs.analyzer.incomestatement;

import com.entity.PropertyType;
import com.jobs.analyzer.Analyzer;
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
}
