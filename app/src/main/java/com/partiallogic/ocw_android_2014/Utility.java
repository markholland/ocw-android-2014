package com.partiallogic.ocw_android_2014;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by markholland on 19/08/14.
 */
public class Utility {

    private static final String LOG_TAG = Utility.class.getSimpleName();

    private static final String SharedPrefsFileName = "OCW";
    public static final String LAST_MODIFIED = "last-modified";
    public static final String LAST_MODIFIED_DEFAULT = "-1";
    public static final String SCHEDULE = "schedule";
    public static final String SPEAKER = "speaker";
    public static final String TRACK = "track";

    private static final DateFormat SourceFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS-'07:00'");

    private static final DateFormat HumanTimeFormatter = new SimpleDateFormat("H:mm");

    private static final DateFormat HumanDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final DateFormat EventDetailFormatter = new SimpleDateFormat("c HH:mm");

    private static final DateFormat NavFormatter = new SimpleDateFormat("cccc dd MMMM yyyy");

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

    public static String getEventDetailTimeLocation (String startTime, String endTime,
                                                     String roomTitle) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(EventDetailFormatter.format(SourceFormatter.parse(startTime)));
            sb.append(" -");
            sb.append(HumanTimeFormatter.format(SourceFormatter.parse(endTime)));
            sb.append(" in ");
            sb.append(roomTitle);
        } catch (ParseException e){
            e.printStackTrace();
        }

        return sb.toString();

    }

    public static String getNavTime(String time) {

        try {
            return NavFormatter.format(HumanDateFormatter.parse(time));
        } catch(ParseException e) {
           e.printStackTrace();
        }

        return null;
    }


    public static void setSharedPrefString(Context context, String key, String value) {

        SharedPreferences sharedPref = context.getSharedPreferences(
                SharedPrefsFileName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedPrefString(Context context, String key, String defaultValue) {

        SharedPreferences sharedPref = context.getSharedPreferences(
                SharedPrefsFileName, Context.MODE_PRIVATE);

        return sharedPref.getString(key, defaultValue);
    }




}
