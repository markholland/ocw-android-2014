package com.partiallogic.ocw_android_2014;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by markholland on 19/08/14.
 */
public class Utility {

    private static final String LOG_TAG = Utility.class.getSimpleName();

    private static final DateFormat SourceFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS-'07:00'");

    private static final DateFormat HumanTimeFormatter = new SimpleDateFormat("h:mm a");

    private static final DateFormat HumanDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String getHumanStartTime(String time) {

        try {
            Date sTime = SourceFormatter.parse(time);
            String humanStartTime = HumanTimeFormatter.format(sTime);
            return humanStartTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateFromStartTime(String time) {

        try {
            Date sTime = SourceFormatter.parse(time);
            //Log.d(LOG_TAG, sTime.toString());
            String sDate = HumanDateFormatter.format(sTime);
            return sDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Date getDateFromDb(String time) {

        try {
            return SourceFormatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
