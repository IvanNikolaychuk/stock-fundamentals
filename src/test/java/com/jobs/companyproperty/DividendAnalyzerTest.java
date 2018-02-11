package com.jobs.companyproperty;

import com.entity.AnalyzeSummary;
import com.entity.CompanyProperty;
import com.entity.Trend;
import com.jobs.analyzer.DividendAnalyzer;
import org.junit.Test;

import static com.entity.PropertyType.DIVIDENDS_PER_SHARE;
import static com.entity.Trend.*;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

public class DividendAnalyzerTest {
    DividendAnalyzer dividendAnalyzer = new DividendAnalyzer(null);

    @Test
    public void positiveWhenDividendsAreGrowingSlowly() {
        assertEquals(
                dividendAnalyzer.analyze(asList(aCompanyProperty(2015, 1d), aCompanyProperty(2016, 1.1d), aCompanyProperty(2017, 1.1d))),
                STABLE_GROWTH
        );
    }

    @Test
    public void zeroFlatWhenDividendsAreAlwaysZero() {
        assertEquals(
                dividendAnalyzer.analyze(asList(aCompanyProperty(2015, 0d), aCompanyProperty(2016, 0d), aCompanyProperty(2017, 0d))),
                ZERO_FLAT
        );
    }

    @Test
    public void negativeWhenDividendsAreDecliningSlowly() {
        assertEquals(
                dividendAnalyzer.analyze(asList(aCompanyProperty(2015, 1d), aCompanyProperty(2016, .9d), aCompanyProperty(2017, .9d))),
                STABLE_DECLINE
        );
    }

    @Test
    public void unstableWhenDividendsAreGrowingWithDeclines() {
        assertEquals(
                dividendAnalyzer.analyze(asList(aCompanyProperty(2015, 1d), aCompanyProperty(2016, .9d), aCompanyProperty(2017, 1.3d))),
                UNSTABLE_GROWTH
        );
    }

    @Test
    public void unstableWhenDividendsAreDecliningWithGrowth() {
        assertEquals(
                dividendAnalyzer.analyze(asList(aCompanyProperty(2015, 1d), aCompanyProperty(2016, 1.1d), aCompanyProperty(2017, .7d))),
                UNSTABLE_DECLINE
        );
    }

    @Test
    public void flatWhenDividendsAreFlat() {
        assertEquals(
                dividendAnalyzer.analyze(asList(aCompanyProperty(2015, 1d), aCompanyProperty(2016, 1d), aCompanyProperty(2017, 1d))),
                FLAT
        );
    }

    private CompanyProperty aCompanyProperty(int year, Double value) {
        return new CompanyProperty("Ticker", year, DIVIDENDS_PER_SHARE, value);
    }
}