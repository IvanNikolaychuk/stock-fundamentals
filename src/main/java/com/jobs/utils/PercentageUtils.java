package com.jobs.utils;

public class PercentageUtils {
    public static double between(Double first, Double second) {
        if (first == null || second == null || first == 0 || second == 0) return 0;
        return (second * 100 / first) - 100;
    }

    public static double betweenAbs(double first, double second) {
        return Math.abs(between(first, second));
    }
}
