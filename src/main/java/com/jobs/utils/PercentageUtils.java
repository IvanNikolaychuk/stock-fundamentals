package com.jobs.utils;

public class PercentageUtils {
    public static double between(double first, double second) {
        return (second * 100 / first) - 100;
    }

    public static double betweenAbs(double first, double second) {
        return Math.abs(between(first, second));
    }
}
