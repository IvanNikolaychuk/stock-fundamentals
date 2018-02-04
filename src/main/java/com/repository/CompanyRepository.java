package com.repository;

import com.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CompanyRepository extends CrudRepository<Company, String> {
}
