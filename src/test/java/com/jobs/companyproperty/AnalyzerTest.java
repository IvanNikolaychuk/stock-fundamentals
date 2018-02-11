package com.jobs.companyproperty;

import com.entity.AnalyzeSummary;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import com.jobs.analyzer.Analyzer;
import com.repository.CompanyPropertyRepository;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.entity.PropertyType.EPS;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AnalyzerTest {
    private static final int MAX_YEARS_TO_ANALYZE = 2;
    private static final PropertyType PROPERTY_TYPE = EPS;
    private static final String TICKER = "SIG";

    private SomeAnalyzer someAnalyzer;

    @Before
    public void setUp() {
        CompanyPropertyRepository companyPropertyRepository = mock(CompanyPropertyRepository.class);
        doReturn(asList(aCompanyProperty(2017, 4d), aCompanyProperty(2016, null), aCompanyProperty(2015, 4d)))
                .when(companyPropertyRepository).findByTickerAndPropertyType(eq(TICKER), eq(PROPERTY_TYPE));
        someAnalyzer = new SomeAnalyzer(companyPropertyRepository);
    }

    private CompanyProperty aCompanyProperty(int year, Double value) {
        return new CompanyProperty(TICKER, year, PROPERTY_TYPE, value);
    }

    @Test
    public void correctValueIsPassedToAnalyzer() {
        someAnalyzer.analyze(TICKER);
        assertEquals(someAnalyzer.companyProperties.size(), 2);
        assertEquals(someAnalyzer.companyProperties.get(0).getYear(), 2016);
        assertEquals(someAnalyzer.companyProperties.get(1).getYear(), 2017);
    }

    public class SomeAnalyzer extends Analyzer {
        public List<CompanyProperty> companyProperties;

        public SomeAnalyzer(CompanyPropertyRepository companyPropertyRepository) {
            super(companyPropertyRepository);
        }

        @Override
        public AnalyzeSummary analyze(List<CompanyProperty> companyProperties) {
            this.companyProperties = companyProperties;
            return null;
        }

        @Override
        public PropertyType getPropertyType() {
            return PROPERTY_TYPE;
        }

        @Override
        public int maxYearsOfAnalysis() {
            return MAX_YEARS_TO_ANALYZE;
        }
    }


}
