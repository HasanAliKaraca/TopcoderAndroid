package com.example.ali.topcoderandroid.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeParser {

    //format: "2015-07-18T00:00:56.971-0400"
    public static String ParseDateTime(String input) {
        //input = "2015-07-18T00:00:00.971-0400";
        Date date = null;
        String output = "";

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(input);

            //output = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a").format(date);
            output = new SimpleDateFormat("dd MMM").format(date);


        } catch (Exception e) {
            LogHelper.Log(e);

        }

        //return date;
        return output;
    }

    //format: "1069639"
    public static Long ParseSecondToHour(long seconds) {

        long hours = TimeUnit.SECONDS.toHours(seconds);

        return hours;
    }

    //format: 1069639
    public static String ParseSecondToDayAndHour(long seconds) {
        //int seconds = 1069639;

        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);

        String s = String.format("%sd %sh %sm", day, hours, minute);

        return s;
    }


}
