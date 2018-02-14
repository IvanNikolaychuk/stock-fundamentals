package com.jobs.companyproperty;

import com.entity.CompanyProperty;
import com.entity.PropertyType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.entity.PropertyType.*;
import static com.entity.PropertyType.TOTAL_DEBT_TO_EQUITY;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DebtToEquityPropertyCreatorTest {
    private DebtToEquityPropertyCreator debtToEquityPropertyCreator;

    @Before
    public void setUp() {
        debtToEquityPropertyCreator = new DebtToEquityPropertyCreator();
    }

    @Test
    public void when_property_is_missing() {
        List<CompanyProperty> companyProperties = asList(aCommonProperty(2017, null, EQUITY), aCommonProperty(2017, 1d, LT_DEBT));

        assertTrue(debtToEquityPropertyCreator.compute("", companyProperties).isEmpty());
    }

    @Test
    public void severalYears() {
        List<CompanyProperty> companyProperties = asList(
                aCommonProperty(2015, 10d, EQUITY), aCommonProperty(2015, 0d, LT_DEBT),
                aCommonProperty(2016, 5d, EQUITY), aCommonProperty(2016, 1d, LT_DEBT),
                aCommonProperty(2017, null, EQUITY), aCommonProperty(2017, 1d, LT_DEBT)
        );

        List<CompanyProperty> properties = debtToEquityPropertyCreator.compute("", companyProperties);
        assertEquals(properties.size(), 2);
        assertTrue(properties.contains(new CompanyProperty("", 2016, LT_DEBT_TO_EQUITY, 0.2)));
        assertTrue(properties.contains(new CompanyProperty("", 2015, LT_DEBT_TO_EQUITY, 0d)));

    }

    public CompanyProperty aCommonProperty(int year, Double value, PropertyType propertyType) {
        return new CompanyProperty("", year, propertyType, value);
    }
}
