package com.api.filter;

import com.api.once.dto.CompanyDto;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class CompanyFilter {
    public static List<CompanyDto> filter(List<CompanyDto> all, Predicate<CompanyDto> predicate) {
        return all.stream()
                .filter(predicate)
                .collect(toList());
    }
}
