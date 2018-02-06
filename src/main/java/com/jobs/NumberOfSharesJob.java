package com.jobs;

import com.api.queries.NumberOfShares;
import com.entity.Company;
import com.entity.CompanyProperty;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import static com.entity.CompanyProperty.Type.NUMBER_OF_SHARES;

@Service
public class NumberOfSharesJob {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyPropertyRepository companyPropertyRepository;
    @Autowired
    private NumberOfShares numberOfShares;

    @PostConstruct
    public void create() {
        List<CompanyProperty> companyProperties = new ArrayList<>();

        for (Company company : companyRepository.findAll()) {
            try {
                CompanyProperty properties = numberOfShares.query(company.getTicker());
                properties.setTicker(company.getTicker());
                properties.setPropertyType(NUMBER_OF_SHARES);

                companyProperties.add(properties);
            } catch (HttpClientErrorException e) {
                System.out.println("Company " + company.getTicker() + " was excluded");
            }
        }

        companyPropertyRepository.save(companyProperties);
        System.out.println("Saved " + companyProperties.size() + " properties");
    }
}
