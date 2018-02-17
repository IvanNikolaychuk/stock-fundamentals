package com.jobs.companyproperty;

import com.entity.CompanyProperty;
import com.entity.PropertyType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.entity.PropertyType.EQUITY;
import static com.entity.PropertyType.LT_DEBT;
import static java.util.stream.Collectors.toSet;

@Component
public class DebtToEquityPropertyCreator {
    public List<CompanyProperty> compute(List<String> tickers, List<CompanyProperty> companyProperties) {
        List<CompanyProperty> properties = new ArrayList<>();
        for(String ticker : tickers) {
            List<CompanyProperty> propertiesForThisTicker = companyProperties.stream()
                    .filter(prop -> prop.getTicker().equals(ticker))
                    .collect(Collectors.toList());

            properties.addAll(compute(ticker, propertiesForThisTicker));
        }

        return properties;
    }

    public List<CompanyProperty> compute(String ticker, List<CompanyProperty> companyProperties) {
        List<CompanyProperty> debtToEq = new ArrayList<>();

        for (Integer year : allYears(companyProperties)) {
            compute(ticker, year, companyProperties).ifPresent(debtToEq::add);
        }

        return debtToEq;
    }

    private Set<Integer> allYears(List<CompanyProperty> companyProperties) {
        return companyProperties.stream()
                .map(CompanyProperty::getYear)
                .collect(toSet());
    }

    private Optional<CompanyProperty> compute(String ticker, int year, List<CompanyProperty> companyProperties) {
        if (!debtAndEquityExist(companyProperties, year)) return Optional.empty();

        final Double equity = find(companyProperties, EQUITY, year).get().getProperty();
        final Double debt = find(companyProperties, LT_DEBT, year).get().getProperty();

        if (debt == 0 || equity == 0)
            return Optional.of(new CompanyProperty(ticker, year, PropertyType.LT_DEBT_TO_EQUITY, 0d));

        return Optional.of(new CompanyProperty(ticker, year, PropertyType.LT_DEBT_TO_EQUITY, debt / equity));
    }

    public boolean debtAndEquityExist(List<CompanyProperty> companyProperties, int year) {
        final Optional<CompanyProperty> debt = find(companyProperties, LT_DEBT, year);
        final Optional<CompanyProperty> equity = find(companyProperties, EQUITY, year);
        return debt.isPresent() && debt.get().getProperty() != null
                && equity.isPresent() && equity.get().getProperty() != null;
    }

    public Optional<CompanyProperty> find(List<CompanyProperty> companyProperties, PropertyType propertyType, int year) {
        return companyProperties.stream()
                .filter(prop -> prop.getPropertyType() == propertyType && prop.getYear() == year)
                .findFirst();
    }
}
