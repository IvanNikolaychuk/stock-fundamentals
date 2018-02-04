package com.api.filter;

import com.api.dto.CompanyDto;

import java.util.List;
import java.util.function.Predicate;

import static com.api.Constants.DELISTED_EXCHANGE;
import static java.util.stream.Collectors.toList;

public class CompanyFilter {
    public static Predicate<CompanyDto> ONLY_LISTED = companyDto -> !companyDto.exchange.equals(DELISTED_EXCHANGE);

    public static List<CompanyDto> filter(List<CompanyDto> all, Predicate<CompanyDto> predicate) {
        return all.stream()
                .filter(predicate)
                .collect(toList());
    }
}
