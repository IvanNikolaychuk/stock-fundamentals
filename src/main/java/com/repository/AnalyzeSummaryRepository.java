package com.repository;

import com.entity.AnalyzeSummary;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnalyzeSummaryRepository extends CrudRepository<AnalyzeSummary, Integer> {
    List<AnalyzeSummary> findByTickerAndProperty(String ticker, PropertyType property);
}
