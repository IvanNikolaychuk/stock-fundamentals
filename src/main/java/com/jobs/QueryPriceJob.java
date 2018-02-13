package com.jobs;

import com.api.config.ApplicationProperties;
import com.api.stockprice.YahooApi;
import com.entity.Company;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import com.entity.StockData;
import com.jobs.companyproperty.PECompanyPropertyCreator;
import com.jobs.companyproperty.strategy.Avg5YearEpsStrategy;
import com.jobs.companyproperty.strategy.LastEpsStrategy;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import com.repository.StockDataRepository;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.entity.PropertyType.AVG_5_YEAR_PE;
import static com.entity.PropertyType.LAST_PE;
import static org.apache.commons.collections4.ListUtils.partition;

@Component
public class QueryPriceJob {
    private static final int ELEMENTS_IN_EACH_SUB_LIST = 500;

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

        stockDataRepository.deleteAll();
        companyPropertyRepository.delete(companyPropertyRepository.findByPropertyType(AVG_5_YEAR_PE));
        companyPropertyRepository.delete(companyPropertyRepository.findByPropertyType(LAST_PE));

        List<Company> companies = new ArrayList<>();
        companyRepository.findAll().forEach(companies::add);

        List<List<Company>> dividedCompanies = partition(companies, ELEMENTS_IN_EACH_SUB_LIST);
        AtomicInteger atomicInteger = new AtomicInteger(dividedCompanies.size());
        for (List<Company> companyList : dividedCompanies) {
            new Thread(new Executor(companyList, atomicInteger)).start();
        }
    }

    public class Executor implements Runnable {
        private List<Company> companies;
        private AtomicInteger counter;

        public Executor(List<Company> companies, AtomicInteger counter) {
            this.companies = companies;
            this.counter = counter;
        }

        @Override
        public void run() {
            List<StockData> stockDatas = new ArrayList<>();
            companies.forEach(company -> yahooApi.queryMostResent(company.getTicker()).ifPresent(stockDatas::add));
            stockDataRepository.save(stockDatas);

            stockDatas.forEach(stockData -> {
                try {
                    peCompanyPropertyCreator.create(stockData, new Avg5YearEpsStrategy()).ifPresent(companyPropertyRepository::save);
                    peCompanyPropertyCreator.create(stockData, new LastEpsStrategy()).ifPresent(companyPropertyRepository::save);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

            System.out.println(counter.addAndGet(-1) + " left");
        }
    }
}
