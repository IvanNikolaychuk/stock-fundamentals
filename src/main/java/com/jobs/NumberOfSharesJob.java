package com.jobs;

import com.api.queries.NumberOfShares;
import com.entity.CompanyProperty;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class NumberOfSharesJob {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyPropertyRepository companyPropertyRepository;
    @Autowired
    private NumberOfShares numberOfShares;

//    @PostConstruct
    public void create() {
        CompanyProperty properties = numberOfShares.query("AAPL");
        properties.setTicker("AAPL");

        companyPropertyRepository.save(properties);
    }
}
