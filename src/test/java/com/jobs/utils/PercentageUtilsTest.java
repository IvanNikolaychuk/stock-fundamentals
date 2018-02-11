package com.jobs.utils;

import org.junit.Test;

import static com.jobs.utils.PercentageUtils.between;
import static com.jobs.utils.PercentageUtils.betweenAbs;
import static org.junit.Assert.assertEquals;

public class PercentageUtilsTest {

    @Test
    public void betweenPositive() {
        assertEquals(between(100, 105), 5, 0.01d);
    }

    @Test
    public void betweenNegative() {
        assertEquals(between(10, 5), -50, 0.01d);
    }

    @Test
    public void betweenModule() {
        assertEquals(betweenAbs(10, 5), 50, 0.01d);
    }
}
