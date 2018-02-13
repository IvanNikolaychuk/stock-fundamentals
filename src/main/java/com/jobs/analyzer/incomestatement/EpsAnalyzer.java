package com.jobs.analyzer.incomestatement;

import com.entity.PropertyType;
import com.jobs.analyzer.Analyzer;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.EPS;

@Service
public class EpsAnalyzer extends Analyzer {
    @Override
    public PropertyType getPropertyType() {
        return EPS;
    }
}
