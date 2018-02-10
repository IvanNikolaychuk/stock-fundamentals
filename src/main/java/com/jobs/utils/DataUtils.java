package com.jobs.utils;

import java.util.Calendar;
import java.util.Date;

public class DataUtils {
    public static int currentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.YEAR);
    }
}
