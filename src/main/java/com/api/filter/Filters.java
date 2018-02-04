package com.api.filter;

import com.api.once.dto.CompanyDto;

import java.util.function.Predicate;

import static com.Constants.DELISTED_EXCHANGE;

public class Filters {
    public static Predicate<CompanyDto> ONLY_LISTED = companyDto -> !companyDto.exchange.equals(DELISTED_EXCHANGE);
}
