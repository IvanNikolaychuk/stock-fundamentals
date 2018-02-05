package com.jobs.once;

import com.api.queries.ListedCompanies;
import com.converter.CompanyDtoToCompany;
import com.entity.Company;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CreateCompanies {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyDtoToCompany companyDtoToCompany;
    @Autowired
    private ListedCompanies listedCompanies;

//    @PostConstruct
    public void create() {
        List<Company> companies = listedCompanies.query()
                .stream()
                .map(companyDtoToCompany::apply)
                .collect(Collectors.toList());

        companyRepository.save(companies);
    }
}
