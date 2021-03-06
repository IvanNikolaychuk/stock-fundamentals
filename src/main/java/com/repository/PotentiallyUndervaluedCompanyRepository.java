package com.repository;

import com.entity.PotentiallyUndervaluedCompany;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface PotentiallyUndervaluedCompanyRepository extends CrudRepository<PotentiallyUndervaluedCompany, Integer> {
}
