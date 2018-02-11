package com.jobs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.jobs.utils.PercentageUtils.between;
import static java.util.stream.Collectors.toList;

public class NumbersUtils {
    public static boolean allNumbersAreZeroOrMore(List<Double> numbers) {
        return numbers.stream().filter(d -> d >= 0).collect(toList()).size() == numbers.size();
    }

    public static Double avgChange(List<Double> numbers) {
        return numbers.stream().mapToDouble(value -> value).average().getAsDouble();
    }

    public static boolean allNumbersAreZeroOrLess(List<Double> numbers) {
        return numbers.stream().filter(d -> d <= 0).collect(toList()).size() == numbers.size();
    }

    public static boolean allNumbersAreZero(List<Double> numbers) {
        return numbers.stream().filter(d -> d == null || d == 0).collect(toList()).size() == numbers.size();
    }

    public static List<Double> percChangeList(List<Double> numbers) {
        List<Double> percentageChangeList = new ArrayList<>();

        for (int i = 0; i < numbers.size() - 1; i++) {
            percentageChangeList.add(between(numbers.get(i), numbers.get(i + 1)));
        }

        return percentageChangeList;
    }

}
