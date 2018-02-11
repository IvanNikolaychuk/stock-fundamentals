package com.jobs.analyzer.trend;

import com.entity.CompanyProperty;
import com.entity.Trend;

import java.util.List;

import static com.entity.Trend.*;
import static com.jobs.utils.NumbersUtils.*;
import static com.jobs.utils.PercentageUtils.betweenAbs;
import static java.util.stream.Collectors.toList;

public class TrendAnalyzer {
    private static final int MIN_NUMBER_OF_RECORDS_REQUIRED = 2;
    private static final int MAX_PERCENTAGE_CHANGE_FOR_FLAT = 5;
    private static final int MIN_PERCENTAGE_AVG_CHANGE_FOR_UNSTABLE_GROWTH = 5;
    private static final int MAX_PERCENTAGE_AVG_CHANGE_FOR_UNSTABLE_DECLINE = -5;

    public static Trend analyze(List<CompanyProperty> companyProperties) {
        if (!enoughRecords(companyProperties)) return UNKNOWN;
        if (isFlat(companyProperties)) return FLAT;
        if (isStableGrowth(companyProperties)) return STABLE_GROWTH;
        if (isUnstableGrowth(companyProperties)) return UNSTABLE_GROWTH;
        if (isStableDecline(companyProperties)) return STABLE_DECLINE;
        if (isUnstableDecline(companyProperties)) return UNSTABLE_DECLINE;

        return UNKNOWN;
    }

    private static boolean isStableDecline(List<CompanyProperty> properties) {
        return allNumbersAreZeroOrLess(percChangeList(toDoubles(properties)));
    }

    private static boolean isUnstableDecline(List<CompanyProperty> properties) {
        return avgChange(percChangeList(toDoubles(properties))) < MAX_PERCENTAGE_AVG_CHANGE_FOR_UNSTABLE_DECLINE;
    }

    private static boolean isStableGrowth(List<CompanyProperty> properties) {
        return allNumbersAreZeroOrMore(percChangeList(toDoubles(properties)));
    }

    private static boolean isUnstableGrowth(List<CompanyProperty> properties) {
        return avgChange(percChangeList(toDoubles(properties))) > MIN_PERCENTAGE_AVG_CHANGE_FOR_UNSTABLE_GROWTH;
    }

    private static boolean isFlat(List<CompanyProperty> properties) {
        List<CompanyProperty> orderedAndNotNull = properties.stream()
                .filter(prop -> prop.getProperty() != null)
                .sorted((first, second) -> first.getYear() - second.getYear())
                .collect(toList());

        final double first = orderedAndNotNull.get(0).getProperty();
        final double second = orderedAndNotNull.get(orderedAndNotNull.size() - 1).getProperty();
        return betweenAbs(first, second) <= MAX_PERCENTAGE_CHANGE_FOR_FLAT;
    }

    private static boolean enoughRecords(List<CompanyProperty> properties) {
        return properties
                .stream()
                .filter(prop -> prop.getProperty() != null)
                .collect(toList())
                .size() >= MIN_NUMBER_OF_RECORDS_REQUIRED;
    }

    private static List<Double> toDoubles(List<CompanyProperty> properties) {
        return properties.stream().map(CompanyProperty::getProperty).collect(toList());
    }
}
