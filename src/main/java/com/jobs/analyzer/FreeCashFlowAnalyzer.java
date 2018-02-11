package com.jobs.analyzer;

import com.entity.PropertyType;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.FREE_CASH_FLOW;

@Service
public class FreeCashFlowAnalyzer extends Analyzer {

    public FreeCashFlowAnalyzer(CompanyPropertyRepository companyPropertyRepository) {
        super(companyPropertyRepository);
    }

    @Override
    public PropertyType getPropertyType() {
        return FREE_CASH_FLOW;
    }
}
