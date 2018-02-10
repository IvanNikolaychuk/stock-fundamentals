package com.jobs;

import com.api.config.ApplicationProperties;
import com.api.stockprice.YahooApi;
import com.entity.CompanyProperty;
import com.entity.StockData;
import com.jobs.companyproperty.PECompanyPropertyCreator;
import com.jobs.companyproperty.strategy.Avg5YearEpsStrategy;
import com.jobs.companyproperty.strategy.LastEpsStrategy;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import com.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class QueryPriceJob {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyPropertyRepository companyPropertyRepository;

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private PECompanyPropertyCreator peCompanyPropertyCreator;

    @Autowired
    private YahooApi yahooApi;

    @Autowired
    private ApplicationProperties properties;

    @PostConstruct
    public void create() {
        if (!properties.isQueryStockPriceJob()) return;

        Iterable<StockData> stockDataList = stockDataRepository.findAll();
//        companyRepository.findAll()
//                .forEach(company -> yahooApi.queryMostResent(company.getTicker()).ifPresent(stockDataList::add));
//        stockDataRepository.save(stockDataList);

        List<CompanyProperty> peProperties = new ArrayList<>();
        stockDataList.forEach(stockData -> {
            peCompanyPropertyCreator.create(stockData, new LastEpsStrategy()).ifPresent(peProperties::add);
            peCompanyPropertyCreator.create(stockData, new Avg5YearEpsStrategy()).ifPresent(peProperties::add);
        });

        companyPropertyRepository.save(peProperties);
    }
}
