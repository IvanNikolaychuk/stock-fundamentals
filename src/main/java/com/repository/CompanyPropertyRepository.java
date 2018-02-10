package com.repository;

import com.entity.Company;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyPropertyRepository extends CrudRepository<CompanyProperty, String> {
    public List<CompanyProperty> find(String ticker, PropertyType propertyType);
}
