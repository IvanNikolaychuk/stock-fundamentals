package com.jobs;

import com.api.config.ApplicationProperties;
import com.api.stockprice.YahooApi;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
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

import static com.entity.PropertyType.AVG_5_YEAR_PE;
import static com.entity.PropertyType.LAST_PE;

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

        List<StockData> stockDataList = new ArrayList<>();
        companyRepository.findAll()
                .forEach(company -> yahooApi.queryMostResent(company.getTicker()).ifPresent(stockDataList::add));
        stockDataRepository.save(stockDataList);

        companyPropertyRepository.delete(companyPropertyRepository.findByPropertyType(AVG_5_YEAR_PE));
        companyPropertyRepository.delete(companyPropertyRepository.findByPropertyType(LAST_PE));

        stockDataList.forEach(stockData -> {
            try {
                peCompanyPropertyCreator.create(stockData, new Avg5YearEpsStrategy()).ifPresent(companyPropertyRepository::save);
                peCompanyPropertyCreator.create(stockData, new LastEpsStrategy()).ifPresent(companyPropertyRepository::save);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
