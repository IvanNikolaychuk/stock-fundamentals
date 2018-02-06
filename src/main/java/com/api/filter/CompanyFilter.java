package com.api.filter;

import com.entity.Company;

import java.util.List;
import java.util.function.Predicate;

import static com.api.Constants.DELISTED_EXCHANGE;
import static java.util.stream.Collectors.toList;

public class CompanyFilter {
    public static Predicate<Company> ONLY_LISTED = company -> !company.getExchange().equals(DELISTED_EXCHANGE);

    public static List<Company> filter(List<Company> all, Predicate<Company> predicate) {
        return all.stream()
                .filter(predicate)
                .collect(toList());
    }
}
