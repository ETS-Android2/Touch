package com.seeds.touch.Configuration;

import java.util.Calendar;
import java.util.StringTokenizer;

public class Converter {
    public static Calendar MYSQLI_StringToCalendar(String date) {
        if (date == null || date.length() == 0)
            return Calendar.getInstance();
        try {
            if (date.indexOf(".") > -1) {
                date = date.substring(0, date.indexOf("."));
            }
            StringTokenizer tokenizer = new StringTokenizer(date, " ");

            String datePart = tokenizer.nextToken();
            String timePart = tokenizer.nextToken();

            tokenizer = new StringTokenizer(datePart, "-");
            String year = tokenizer.nextToken();
            String month = tokenizer.nextToken();
            String day = tokenizer.nextToken();

            tokenizer = new StringTokenizer(timePart, ":");
            String hours = tokenizer.nextToken();
            String minutes = tokenizer.nextToken();
            String seconds = tokenizer.nextToken();

            Calendar finalDate = Calendar.getInstance();
            finalDate.set(Calendar.YEAR, Integer.parseInt(year));
            finalDate.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            finalDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

            finalDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
            finalDate.set(Calendar.MINUTE, Integer.parseInt(minutes));
            finalDate.set(Calendar.SECOND, Integer.parseInt(seconds));
            return finalDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
