package com.api.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {
    private static final SimpleDateFormat FORMATTING = new SimpleDateFormat("yyyy-mm-dd");

    public static Date convert(String dateString) {
        try {
            return dateString == null ? null : FORMATTING.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
