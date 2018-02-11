package com.jobs.companyproperty;

import com.entity.CompanyProperty;
import com.entity.PropertyType;
import com.entity.StockData;
import com.jobs.companyproperty.strategy.Avg5YearEpsStrategy;
import com.jobs.companyproperty.strategy.LastEpsStrategy;
import com.repository.CompanyPropertyRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.entity.PropertyType.*;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class PECompanyPropertyCreatorTest {
    private static final String TICKER = "ticker";
    private CompanyPropertyRepository companyPropertyRepository;
    private PECompanyPropertyCreator peCompanyPropertyCreator;

    @Before
    public void setUp() {
        companyPropertyRepository = mock(CompanyPropertyRepository.class);
        peCompanyPropertyCreator = new PECompanyPropertyCreator(companyPropertyRepository);
    }

    @Test
    public void avg_6_pe_property() {
        List<CompanyProperty> properties = Arrays.asList(epsProperty(2017, 6d), epsProperty(2016, 0d),
                epsProperty(2015, 6d), epsProperty(2014, 0d), epsProperty(2013, 3d), epsProperty(2012, 50d));
        doReturn(properties).when(companyPropertyRepository).findByTickerAndPropertyType(eq(TICKER), eq(EPS));

        Optional<CompanyProperty> avg5YearPe = peCompanyPropertyCreator.create(stockData(30), new Avg5YearEpsStrategy());
        assertProperty(avg5YearPe.get(), 10, AVG_5_YEAR_PE);
    }

    @Test
    public void last_pe_property() {
        doReturn(singletonList(epsProperty(2017, 1d))).when(companyPropertyRepository).findByTickerAndPropertyType(eq(TICKER), eq(EPS));
        Optional<CompanyProperty> peProperty = peCompanyPropertyCreator.create(stockData(10), new LastEpsStrategy());
        assertProperty(peProperty.get(), 10, LAST_PE);
    }

    @Test
    public void allPropertiesAreNull() {
        List<CompanyProperty> properties = singletonList(epsProperty(2017, null));
        doReturn(properties).when(companyPropertyRepository).findByTickerAndPropertyType(eq(TICKER), eq(EPS));
        Optional<CompanyProperty> pePropertiy = peCompanyPropertyCreator.create(stockData(10), new LastEpsStrategy());

        assertFalse(pePropertiy.isPresent());
    }

    public void assertProperty(CompanyProperty companyProperty, double expectedPe, PropertyType expectedPropertyType) {
        assertEquals(companyProperty.getProperty(), expectedPe);
        assertEquals(companyProperty.getPropertyType(), expectedPropertyType);
    }

    private StockData stockData(double price) {
        StockData stockData = new StockData();
        stockData.setClose(price);
        stockData.setTicker(TICKER);
        return stockData;
    }


    public CompanyProperty epsProperty(int year, Double value) {
        return new CompanyProperty("", year, EPS, value);
    }
}
