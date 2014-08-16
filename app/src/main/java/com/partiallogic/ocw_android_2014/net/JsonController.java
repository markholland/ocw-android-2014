package com.partiallogic.ocw_android_2014.net;

import android.content.Context;

import com.partiallogic.ocw_android_2014.obj.EventItem;
import com.partiallogic.ocw_android_2014.obj.Schedule;
import com.partiallogic.ocw_android_2014.obj.Speaker;
import com.partiallogic.ocw_android_2014.obj.SpeakerData;
import com.partiallogic.ocw_android_2014.obj.Track;
import com.partiallogic.ocw_android_2014.obj.TrackData;

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

        List<EventItem> events = sched.getEventItems();

        /*
        for(int i = 0; i < events.size(); i++) {
            Log.d(LOG_TAG, events.get(i).getEvent().toString());
        }
        */

        return sched;
    }

    public List<Track> getTracks(ServiceClient serviceClient, Context con) {
        ApiClient client = serviceClient.getClient(con, ApiClient.class);
        List<TrackData> trackDataList = client.getTracks();
        ArrayList<Track> tracks = new ArrayList<Track>();


        for(int i = 0; i < trackDataList.size(); i++) {
            tracks.add(trackDataList.get(i).getTrack());
        }


        return tracks;
    }

    public Speaker getSpeakerById(ServiceClient serviceClient, Context con, String id) {
        ApiClient client = serviceClient.getClient(con, ApiClient.class);
        SpeakerData speakerData = client.getSpeakerById(id);

        Speaker speaker = speakerData.getSpeaker();

        //Log.d(LOG_TAG, speaker.toString());

        return speaker;
    }


}
