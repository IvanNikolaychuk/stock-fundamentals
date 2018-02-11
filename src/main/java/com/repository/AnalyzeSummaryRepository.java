package com.repository;

import com.entity.AnalyzeSummary;
import com.entity.CompanyProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AnalyzeSummaryRepository extends CrudRepository<AnalyzeSummary, Integer> {
}
