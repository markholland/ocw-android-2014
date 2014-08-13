package com.partiallogic.ocw_android_2014.net;

import android.content.Context;
import android.util.Log;

import com.partiallogic.ocw_android_2014.Data;
import com.partiallogic.ocw_android_2014.Schedule;
import com.partiallogic.ocw_android_2014.Speaker;
import com.partiallogic.ocw_android_2014.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markholland on 13/08/14.
 */
public class JsonController {

    private final String LOG_TAG = JsonController.class.getSimpleName();


    private static JsonController instance;

    public static JsonController getInstance() {

        if(instance == null) {
            instance = new JsonController();
        }
        return instance;
    }

    public JsonController() {

    }

    public Schedule getSchedule(ServiceClient serviceClient, Context con) {
        ApiClient client = serviceClient.getClient(con, ApiClient.class);
        Schedule sched = client.getSchedule();
        return sched;
    }

    public List<Track> getTracks(ServiceClient serviceClient, Context con) {
        ApiClient client = serviceClient.getClient(con, ApiClient.class);
        List<Data> dataList = client.getTracks();
        Log.d(LOG_TAG, "SIZE: "+dataList.size());

        ArrayList<Track> tracks = new ArrayList<Track>();

        for(int i = 0; i < dataList.size(); i++) {
            tracks.add(dataList.get(i).getTrack());
            Log.d(LOG_TAG, dataList.get(i).getTrack().getId());
        }

        for(int i = 0; i < tracks.size(); i++) {
            Log.d(LOG_TAG, tracks.get(i).toString());
        }

        return tracks;
    }

    public Speaker getSpeakerById(ServiceClient serviceClient, Context con, String id) {
        ApiClient client = serviceClient.getClient(con, ApiClient.class);
        Speaker speaker = client.getSpeakerById();
        return speaker;
    }


}
