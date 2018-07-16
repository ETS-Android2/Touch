package com.seeds.touch.Configuration;

import com.android.volley.toolbox.StringRequest;

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

    public static String toCamelCase(String text) {
        if (text == null)
            return "Null";

        final StringBuilder ret = new StringBuilder(text.length());

        for (final String word : text.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == text.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static String getDifferenceBetweenCalendars(Calendar calendarPrecede, Calendar calendarBefore) {

        long difference = calendarPrecede.getTimeInMillis() - calendarBefore.getTimeInMillis();
        int hours = (int) (difference / (1000 * 60 * 60));
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int months = (int) (difference / (1000 * 60 * 60 * 24 * 30));
        int years = (int) (difference / (1000 * 60 * 60 * 24 * 30 * 12));

        if (years != 0) {

            return Math.abs(years) + " years "+(years<0 ? "ago " : "");

        }
            else if (months != 0)
            return Math.abs(months) + " months "+(months<0 ? "ago" : "");
        else if (days != 0)
            return Math.abs(days) + " days "+(days<0 ? "ago" : "");
        else
            return Math.abs(hours) + " hours "+(hours<0 ? "ago" : "");

    }
}
