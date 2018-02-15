package com.jobs.once;

import com.api.config.ApplicationProperties;
import com.api.queries.ListedCompaniesQuery;
import com.entity.Company;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class CreateCompanies {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ListedCompaniesQuery listedCompanies;

    @Autowired
    private ApplicationProperties applicationProperties;

    @PostConstruct
    public void create() {
        if (!applicationProperties.isCreateCompaniesJob()) return;
        companyRepository.save(listedCompanies.query());
        System.out.println(CreateCompanies.this.getClass().getName() + " finished");
    }
}
