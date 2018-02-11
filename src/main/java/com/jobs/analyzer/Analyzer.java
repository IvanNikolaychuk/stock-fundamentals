package com.jobs.analyzer;

import com.entity.AnalyzeSummary;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import com.jobs.utils.DataUtils;
import com.repository.CompanyPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Component
public abstract class Analyzer {
    private CompanyPropertyRepository companyPropertyRepository;

    public Analyzer(CompanyPropertyRepository companyPropertyRepository) {
        this.companyPropertyRepository = companyPropertyRepository;
    }

    public abstract AnalyzeSummary analyze(List<CompanyProperty> companyProperties);

    public AnalyzeSummary analyze(String ticker) {
        final int minYear = DataUtils.currentYear() - maxYearsOfAnalysis();

        List<CompanyProperty> orderedByYear = companyPropertyRepository
                .findByTickerAndPropertyType(ticker, getPropertyType()).stream()
                .filter(property -> property.getYear() >= minYear)
                .sorted((first, second) -> first.getYear() - second.getYear())
                .collect(toList());

        return analyze(orderedByYear);
    }

    public abstract PropertyType getPropertyType();

    public abstract int maxYearsOfAnalysis();
}
