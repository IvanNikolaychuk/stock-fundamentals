package com.jobs;

import com.api.config.ApplicationProperties;
import com.entity.*;
import com.repository.AnalyzeSummaryRepository;
import com.repository.CompanyPropertyRepository;
import com.repository.CompanyRepository;
import com.repository.PotentiallyUndervaluedCompanyRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static com.entity.PropertyType.LAST_PE;
import static com.entity.PropertyType.LT_DEBT_TO_EQUITY;

@Component
public class PotentiallyUndervaluedCompaniesJob {
    private static final Double MAX_ACCEPTED_PE = 12d;
    private static final Double MAX_ACCEPTED_DE = 0.5d;

    @Autowired
    private PotentiallyUndervaluedCompanyRepository potentiallyUndervaluedCompanyRepository;

    @Autowired
    private CompanyPropertyRepository companyPropertyRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    @PostConstruct
    public void create() {
        if(!applicationProperties.isPotentiallyUndervaluedCompaniesJob()) return;

        potentiallyUndervaluedCompanyRepository.deleteAll();
        List<String> goodLastPe = companyPropertyRepository.findWithGoodLastPe(MAX_ACCEPTED_PE);
        List<String> goodAvgPe = companyPropertyRepository.findWithGoodAvgPe(MAX_ACCEPTED_PE);
        List<String> veryGoodPe = new ArrayList<>(CollectionUtils.intersection(goodLastPe, goodAvgPe));

        List<String> withSmallDebt = filterWithSmallDebt(veryGoodPe);
        List<PotentiallyUndervaluedCompany> potentiallyUndervalued =
                withSmallDebt.stream().map(PotentiallyUndervaluedCompany::new).collect(Collectors.toList());

        potentiallyUndervaluedCompanyRepository.save(potentiallyUndervalued);
    }

    private List<String> filterWithSmallDebt(List<String> tickers) {
        List<String> withSmallDebt = new ArrayList<>();

        for (String ticker : tickers) {
            final Optional<CompanyProperty> debtToEquity = findLatest(ticker, LT_DEBT_TO_EQUITY);
            if (debtToEquity.isPresent()) {
                if (debtToEquity.get().getProperty() < MAX_ACCEPTED_DE) withSmallDebt.add(ticker);
            } else {
                System.err.println("No " + LT_DEBT_TO_EQUITY.name() + " found for " + ticker);
                withSmallDebt.add(ticker);
            }

        }

        return withSmallDebt;
    }

    private Optional<CompanyProperty> findLatest(String ticker, PropertyType propertyType) {
        return companyPropertyRepository.findByTickerAndPropertyType(ticker, propertyType)
                .stream()
                .sorted((first, second) -> second.getYear() - first.getYear())
                .findFirst();
    }
}
