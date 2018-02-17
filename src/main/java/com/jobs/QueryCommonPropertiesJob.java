package com.jobs;

import com.api.config.ApplicationProperties;
import com.api.queries.GenericCommonPropertyQuery;
import com.entity.Company;
import com.entity.CompanyProperty;
import com.jobs.companyproperty.DebtToEquityPropertyCreator;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class QueryCommonPropertiesJob {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyPropertyRepository companyPropertyRepository;
    @Autowired
    private GenericCommonPropertyQuery genericCommonPropertyQuery;
    @Autowired
    private ApplicationProperties properties;
    @Autowired
    private DebtToEquityPropertyCreator debtToEquityPropertyCreator;

    private static String TEMPLATE_URL = "https://www.quandl.com/api/v3/datatables/SHARADAR/SF1.json?" +
            "ticker={0}&qopts.columns=ticker,datekey,revenue,netinc,shareswa,workingcapital,dps,fcf,TBVPS,EPSDIL,EQUITYUSD,DEBTNC" +
            "&dimension=ARY&api_key={1}";

    @PostConstruct
    public void query() {
        if (!properties.isQueryCommonPropertiesJob()) return;
        System.out.println(this.getClass().getName() + " started");

        for (String tickers : splitByTickers(companyRepository.findAll())) {
            final String url = MessageFormat.format(TEMPLATE_URL, tickers, properties.getApiKey());
            List<CompanyProperty> companyProperties = genericCommonPropertyQuery.query(url);
            companyProperties.addAll(debtToEquityPropertyCreator.compute(asList(tickers.split(",")), companyProperties));
            companyPropertyRepository.save(companyProperties);
        }

        System.out.println(this.getClass().getName() + " finished");
    }

    private List<String> splitByTickers(Iterable<Company> companies) {
        List<String> tickersList = new ArrayList<>();


        int counter = 0;
        String tickers = "";
        for (Company company : companies) {
            ++counter;
            tickers += company.getTicker();
            if (counter % 100 == 0) {
                tickersList.add(tickers);
                tickers = "";
            } else {
                tickers += ",";
            }
        }

        return tickersList;
    }
}
