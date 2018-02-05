package com.api.utils;

import com.api.converter.StringToDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class YearFromDateExtractor {
    public static int get(String dateString) {
        Date date = StringToDate.convert(dateString);
        if (date == null) throw new IllegalStateException(dateString + " cannot be converted to date.");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
