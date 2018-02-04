package com.jobs.once;

import com.api.dto.CompanyDto;
import com.api.queries.ListedCompanies;
import com.converter.CompanyDtoToCompany;
import com.entity.Company;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static com.api.queries.ListedCompanies.queryAllListed;

@Service
public class CreateCompanies {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyDtoToCompany companyDtoToCompany;

//    @PostConstruct
    public void create() {
        List<Company> companies = queryAllListed()
                .stream()
                .map(companyDtoToCompany::apply)
                .collect(Collectors.toList());

        companyRepository.save(companies);
    }
}
