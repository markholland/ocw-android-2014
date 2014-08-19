package com.partiallogic.ocw_android_2014;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by markholland on 19/08/14.
 */
public class Utility {

    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getHumanStartTime(String startTime) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS-'07:00'");
        try {
            Date sTime = formatter.parse(startTime);
            Log.d(LOG_TAG, sTime.toString());
            DateFormat formatter2 = new SimpleDateFormat("h:mm a");
            String humanStartTime = formatter2.format(sTime);
            return humanStartTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
