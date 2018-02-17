package com.repository;

import com.entity.Company;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyPropertyRepository extends CrudRepository<CompanyProperty, String> {
    public List<CompanyProperty> findByTickerAndPropertyType(String ticker, PropertyType propertyType);
    public List<CompanyProperty> findByPropertyType(PropertyType propertyType);
    List<CompanyProperty> findByTicker(String ticker);
    @Query(value = "SELECT DISTINCT ticker FROM  company_property" +
                    " WHERE property_type='LAST_PE' AND (property > 0 AND property < ?1)", nativeQuery = true)
    List<String> findWithGoodLastPe(double maxPe);
    @Query(value = "SELECT DISTINCT ticker FROM  company_property" +
                    " WHERE property_type='AVG_5_YEAR_PE' AND (property > 0 AND property < ?1)", nativeQuery = true)
    List<String> findWithGoodAvgPe(double maxPe);
}
