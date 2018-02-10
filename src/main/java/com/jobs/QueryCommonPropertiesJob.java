package com.jobs;

import com.api.config.ApplicationProperties;
import com.api.queries.GenericCommonPropertyQuery;
import com.entity.Company;
import com.entity.CompanyProperty;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

    private static String TEMPLATE_URL = "https://www.quandl.com/api/v3/datatables/SHARADAR/SF1.json?" +
            "ticker={0}&qopts.columns=ticker,datekey,revenue,netinc,shareswa,workingcapital,dps,fcf,de,TBVPS,EPSDIL" +
            "&dimension=ARY&api_key={1}";

    @PostConstruct
    public void query() {
        if (!properties.isQueryCommonPropertiesJob()) return;
        List<String> tickerList = splitByTickers(companyRepository.findAll());
        for (String tickers : tickerList) {
            final String url = MessageFormat.format(TEMPLATE_URL, tickers, properties.getApiKey());
            List<CompanyProperty> companyProperties = genericCommonPropertyQuery.query(url);
            companyPropertyRepository.save(companyProperties);
        }
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
