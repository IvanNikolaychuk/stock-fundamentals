package com.converter;

import com.api.dto.CompanyDto;
import com.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CompanyDtoToCompany implements Function<CompanyDto, Company> {

    @Override
    public Company apply(CompanyDto companyDto) {
        final Company company = new Company();

        company.setSector(companyDto.sector);
        company.setIndustry(companyDto.industry);
        company.setTicker(companyDto.ticker);
        company.setExchange(companyDto.exchange);
        company.setFirstAdded(companyDto.firstAdded);
        company.setLastUpdated(companyDto.lastUpdated);

        return company;
    }
}
