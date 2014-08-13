package com.partiallogic.ocw_android_2014.net;

import com.partiallogic.ocw_android_2014.obj.Schedule;
import com.partiallogic.ocw_android_2014.obj.SpeakerData;
import com.partiallogic.ocw_android_2014.obj.TrackData;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by markholland on 13/08/14.
 */
public interface ApiClient {

    // Schedule
    @GET("/schedule.json")
    Schedule getSchedule();




    // Tracks
    @GET("/tracks.json")
    List<TrackData> getTracks();




    // Speaker
    @GET("/users/{id}.json")
    SpeakerData getSpeakerById(@Path("id") String _id);


}
