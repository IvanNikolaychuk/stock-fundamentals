package com.jobs;

import com.api.stockprice.YahooApi;
import com.entity.StockData;
import com.repository.CompanyRepository;
import com.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockPriceJob {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private YahooApi yahooApi;

    @PostConstruct
    public void create() {
        List<StockData> stockData = new ArrayList<>();

        companyRepository.findAll()
                .forEach(company -> yahooApi.queryMostResent(company.getTicker()).ifPresent(stockData::add));
        stockDataRepository.save(stockData);
    }
}
