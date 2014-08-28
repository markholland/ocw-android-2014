package com.partiallogic.ocw_android_2014.net;

import com.partiallogic.ocw_android_2014.obj.Schedule;
import com.partiallogic.ocw_android_2014.obj.SpeakerData;
import com.partiallogic.ocw_android_2014.obj.TrackData;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by markholland on 13/08/14.
 */
public interface ApiClient {

    public static final String SCHEDULE_URI = "/schedule.json";
    public static final String TRACK_URI = "/tracks.json";
    public static final String SPEAKER_URI = "/events/2014/speakers.json";

    // Schedule
    @GET(SCHEDULE_URI)
    Schedule getSchedule();



    // Tracks
    @GET(TRACK_URI)
    List<TrackData> getTracks();



    // Speaker
    @GET(SPEAKER_URI)
    List<SpeakerData> getSpeakers();


}
