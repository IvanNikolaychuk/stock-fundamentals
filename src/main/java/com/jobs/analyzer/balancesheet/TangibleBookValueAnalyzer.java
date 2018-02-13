package com.jobs.analyzer.balancesheet;

import com.entity.PropertyType;
import com.jobs.analyzer.Analyzer;
import com.repository.CompanyPropertyRepository;
import org.springframework.stereotype.Service;

import static com.entity.PropertyType.TANGIBLE_BOOK_VALUE_PER_SHARE;

@Service
public class TangibleBookValueAnalyzer extends Analyzer {
    @Override
    public PropertyType getPropertyType() {
        return TANGIBLE_BOOK_VALUE_PER_SHARE;
    }
}
