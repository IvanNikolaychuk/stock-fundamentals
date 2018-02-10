package com.jobs;

import com.api.config.ApplicationProperties;
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
public class QueryPriceJob {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private YahooApi yahooApi;

    @Autowired
    private ApplicationProperties properties;

    @PostConstruct
    public void create() {
        if (!properties.isQueryStockPriceJob()) return;

        List<StockData> stockData = new ArrayList<>();
        companyRepository.findAll()
                .forEach(company -> yahooApi.queryMostResent(company.getTicker()).ifPresent(stockData::add));
        stockDataRepository.save(stockData);
    }
}
