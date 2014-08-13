package com.partiallogic.ocw_android_2014.net;

import com.partiallogic.ocw_android_2014.Data;
import com.partiallogic.ocw_android_2014.Schedule;
import com.partiallogic.ocw_android_2014.Speaker;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by markholland on 13/08/14.
 */
public interface ApiClient {

    // Schedule
    @GET("/schedule")
    Schedule getSchedule();




    // Tracks
    @GET("/tracks.json")
    List<Data> getTracks();




    // Speaker
    @GET("/users/1.json")
    Speaker getSpeakerById();


}
