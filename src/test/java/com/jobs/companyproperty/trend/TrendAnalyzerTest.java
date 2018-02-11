package com.jobs.companyproperty.trend;

import com.entity.CompanyProperty;
import com.jobs.analyzer.trend.TrendAnalyzer;
import org.junit.Test;

import java.util.List;

import static com.entity.PropertyType.EPS;
import static com.jobs.analyzer.trend.TrendAnalyzer.Trend.*;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

public class TrendAnalyzerTest {

    @Test
    public void unknownWhenLessThan2Records() {
        List<CompanyProperty> properties = asList(property(2015, 3d), property(2016, null));
        assertEquals(TrendAnalyzer.analyze(properties), UNKNOWN);
    }

    @Test
    public void stableDeclineWithMissingRecords() {
        List<CompanyProperty> properties = asList(property(2015, null), property(2016, 3d), property(2017, 2d));
        assertEquals(TrendAnalyzer.analyze(properties), STABLE_DECLINE);
    }

    @Test
    public void stableDecline() {
        List<CompanyProperty> properties = asList(property(2015, 3.5d), property(2016, 3d), property(2017, 2d));
        assertEquals(TrendAnalyzer.analyze(properties), STABLE_DECLINE);
    }

    @Test
    public void stableGrowthWithMissingRecords() {
        List<CompanyProperty> properties = asList(property(2015, null), property(2016, 3d), property(2017, 6d));
        assertEquals(TrendAnalyzer.analyze(properties), STABLE_GROWTH);
    }

    @Test
    public void stableGrowth() {
        List<CompanyProperty> properties = asList(property(2015, 3d), property(2016, 4d), property(2017, 5d));
        assertEquals(TrendAnalyzer.analyze(properties), STABLE_GROWTH);
    }

    @Test
    public void unstableGrowthWithMissingRecords() {
        List<CompanyProperty> properties = asList(property(2015, null), property(2016, 3d), property(2017, 3.5d));
        assertEquals(TrendAnalyzer.analyze(properties), UNSTABLE_GROWTH);
    }

    @Test
    public void unstableGrowth() {
        List<CompanyProperty> properties = asList(property(2015, 3d), property(2016, 2.9d), property(2017, 3.6d));
        assertEquals(TrendAnalyzer.analyze(properties), UNSTABLE_GROWTH);
    }


    @Test
    public void unstableDeclineWithMissingRecords() {
        List<CompanyProperty> properties = asList(property(2015, null), property(2016, 3d), property(2017, 2.5d));
        assertEquals(TrendAnalyzer.analyze(properties), UNSTABLE_DECLINE);
    }

    @Test
    public void unstableDecline() {
        List<CompanyProperty> properties = asList(property(2015, 3d), property(2016, 3.1d), property(2017, 2.4d));
        assertEquals(TrendAnalyzer.analyze(properties), UNSTABLE_DECLINE);
    }

    @Test
    public void flatTrendWithMissingRecords() {
        List<CompanyProperty> properties = asList(property(2015, 3d), property(2016, null), property(2017, 3.1d));
        assertEquals(TrendAnalyzer.analyze(properties), FLAT);
    }

    @Test
    public void flatTrend() {
        List<CompanyProperty> properties = asList(property(2015, 3d), property(2016, 4d), property(2017, 3.1d));
        assertEquals(TrendAnalyzer.analyze(properties), FLAT);
    }

    private static CompanyProperty property(int year, Double value) {
        return new CompanyProperty("SIG", year, EPS, value);
    }
}
