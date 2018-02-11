package com.jobs.analyzer;

import com.entity.PropertyType;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.TANGIBLE_BOOK_VALUE_PER_SHARE;

@Service
public class TangibleBookValueAnalyzer extends Analyzer {
    public TangibleBookValueAnalyzer(CompanyPropertyRepository companyPropertyRepository) {
        super(companyPropertyRepository);
    }

    @Override
    public PropertyType getPropertyType() {
        return TANGIBLE_BOOK_VALUE_PER_SHARE;
    }
}
