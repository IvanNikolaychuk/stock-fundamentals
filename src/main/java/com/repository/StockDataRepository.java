package com.repository;

import com.entity.StockData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface StockDataRepository extends CrudRepository<StockData, Integer> {
}
