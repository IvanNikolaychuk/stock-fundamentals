package com.repository;

import com.entity.Company;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyPropertyRepository extends CrudRepository<CompanyProperty, String> {
    public List<CompanyProperty> findByTickerAndPropertyType(String ticker, PropertyType propertyType);
    public List<CompanyProperty> findByPropertyType(PropertyType propertyType);
    List<CompanyProperty> findByTicker(String ticker);
}
