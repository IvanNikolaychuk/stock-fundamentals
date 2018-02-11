package com.jobs.analyzer;

import com.entity.PropertyType;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.REVENUE;

@Service
public class RevenueAnalyzer extends Analyzer {
    public RevenueAnalyzer(CompanyPropertyRepository companyPropertyRepository) {
        super(companyPropertyRepository);
    }

    @Override
    public PropertyType getPropertyType() {
        return REVENUE;
    }
}
