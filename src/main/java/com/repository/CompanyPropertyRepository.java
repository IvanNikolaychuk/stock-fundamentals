package com.repository;

import com.entity.Company;
import com.entity.CompanyProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CompanyPropertyRepository extends CrudRepository<CompanyProperty, String> {
}
