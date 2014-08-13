package com.partiallogic.ocw_android_2014;

import android.content.Context;
import android.os.AsyncTask;

import com.partiallogic.ocw_android_2014.net.JsonController;
import com.partiallogic.ocw_android_2014.net.ServiceClient;

import java.util.List;

/**
 * Created by markholland on 13/08/14.
 */
public class ScheduleTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = ScheduleTask.class.getSimpleName();

    private final Context mContext;

    public ScheduleTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        //Speaker speaker = JsonController.getInstance().getSpeakerById(ServiceClient.getInstance(),
        //        mContext, "1");

        //Log.d(LOG_TAG, speaker.toString());

        List<Track> tracks = JsonController.getInstance().getTracks(ServiceClient.getInstance(),
                mContext);



        return null;
    }
}
