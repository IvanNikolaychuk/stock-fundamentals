package com.jobs.once;

import com.api.queries.ListedCompaniesQuery;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class CreateCompanies {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ListedCompaniesQuery listedCompanies;

//    @PostConstruct
    public void create() {
        companyRepository.save(listedCompanies.query());
    }
}
