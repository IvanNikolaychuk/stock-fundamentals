package com.jobs;

import com.api.config.ApplicationProperties;
import com.entity.AnalyzeSummary;
import com.entity.Company;
import com.entity.CompanyProperty;
import com.jobs.analyzer.balancesheet.TangibleBookValueAnalyzer;
import com.jobs.analyzer.balancesheet.TotalDebtToEquityAnalyzer;
import com.jobs.analyzer.cashflow.FreeCashFlowAnalyzer;
import com.jobs.analyzer.incomestatement.*;
import com.repository.AnalyzeSummaryRepository;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class AnalyzeSummaryJob {
    private static final int ELEMENTS_IN_EACH_SUB_LIST = 500;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyPropertyRepository companyPropertyRepository;

    @Autowired
    private AnalyzeSummaryRepository analyzeSummaryRepository;

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private DividendAnalyzer dividendAnalyzer;

    @Autowired
    private FreeCashFlowAnalyzer freeCashFlowAnalyzer;

    @Autowired
    private EpsAnalyzer epsAnalyzer;

    @Autowired
    private NumberOfSharesAnalyzer numberOfSharesAnalyzer;

    @Autowired
    private NetIncomeAnalyzer netIncomeAnalyzer;

    @Autowired
    private RevenueAnalyzer revenueAnalyzer;

    @Autowired
    private TangibleBookValueAnalyzer tangibleBookValueAnalyzer;

    @Autowired
    private TotalDebtToEquityAnalyzer totalDebtToEquityAnalyzer;

    @PostConstruct
    public void create() {
        if (!properties.isAnalyzeSummaryJob()) return;

        List<Company> companies = new ArrayList<>();
        companyRepository.findAll().forEach(companies::add);
        analyzeSummaryRepository.deleteAll();

        List<List<Company>> dividedCompanies = ListUtils.partition(companies, ELEMENTS_IN_EACH_SUB_LIST);
        AtomicInteger atomicInteger = new AtomicInteger(dividedCompanies.size());
        for (List<Company> companyList : dividedCompanies) {
            new Thread(new AnalyzerThread(companyList, atomicInteger)).start();
        }
    }

    public class AnalyzerThread implements Runnable {
        private List<Company> companies;
        private AtomicInteger counter;

        public AnalyzerThread(List<Company> companies, AtomicInteger counter) {
            this.companies = companies;
            this.counter = counter;
        }

        @Override
        public void run() {
            List<AnalyzeSummary> analyzeSummaries = new ArrayList<>();
            companies.forEach(company -> {
                List<CompanyProperty> properties = companyPropertyRepository.findByTicker(company.getTicker());

                analyzeSummaries.add(dividendAnalyzer.analyze(company.getTicker(), properties));
                analyzeSummaries.add(freeCashFlowAnalyzer.analyze(company.getTicker(), properties));
                analyzeSummaries.add(numberOfSharesAnalyzer.analyze(company.getTicker(), properties));
                analyzeSummaries.add(epsAnalyzer.analyze(company.getTicker(), properties));
                analyzeSummaries.add(netIncomeAnalyzer.analyze(company.getTicker(), properties));
                analyzeSummaries.add(revenueAnalyzer.analyze(company.getTicker(), properties));
                analyzeSummaries.add(tangibleBookValueAnalyzer.analyze(company.getTicker(), properties));
                analyzeSummaries.add(totalDebtToEquityAnalyzer.analyze(company.getTicker(), properties));
            });
            analyzeSummaryRepository.save(analyzeSummaries);
            System.out.println(counter.addAndGet(-1) + " left");
        }
    }
}
