package com.jobs.analyzer;

import com.entity.AnalyzeSummary;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import com.entity.Trend;
import com.jobs.analyzer.trend.TrendAnalyzer;
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
    private CompanyPropertyRepository companyPropertyRepository; // TODO: remove

    public Analyzer(CompanyPropertyRepository companyPropertyRepository) {
        this.companyPropertyRepository = companyPropertyRepository;
    }

    public Trend analyze(List<CompanyProperty> companyProperties) {
        return TrendAnalyzer.analyze(companyProperties);
    }

    public AnalyzeSummary analyze(String ticker, List<CompanyProperty> properties) {
        final int minYear = DataUtils.currentYear() - maxYearsOfAnalysis();

        List<CompanyProperty> orderedByYear = properties.stream()
                .filter(property -> property.getYear() >= minYear && property.getPropertyType() == getPropertyType())
                .sorted((first, second) -> first.getYear() - second.getYear())
                .collect(toList());

        return new AnalyzeSummary(ticker, getPropertyType(), analyze(orderedByYear));
    }

    public abstract PropertyType getPropertyType();

    public int maxYearsOfAnalysis() {
        return 9;
    }
}
