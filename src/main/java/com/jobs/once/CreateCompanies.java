package com.jobs.once;

import com.api.queries.ListedCompanies;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreateCompanies {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ListedCompanies listedCompanies;

//    @PostConstruct
    public void create() {
        companyRepository.save(listedCompanies.query());
    }
}
