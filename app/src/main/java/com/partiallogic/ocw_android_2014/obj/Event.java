package com.partiallogic.ocw_android_2014.obj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by markholland on 13/08/14.
 */
public class Event {

    private String id;
    private String title;
    private String description;
    private String start_time;
    private String end_time;
    private String room_title;
    private String track_id;
    @SerializedName("user_ids")
    private List<String> speaker_ids;

    public Event(String id, String title, String description, String start_time,
                 String end_time, String room_title, String track_id,
                 List<String> speaker_ids) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.room_title = room_title;
        this.track_id = track_id;
        this.speaker_ids = speaker_ids;

    }

    @Override
    public String toString() {
        String str = "";

        str += this.id + "\n" +
                this.title + "\n" +
                this.description + "\n" +
                this.start_time + "\n" +
                this.end_time + "\n" +
                this.room_title + "\n" +
                this.track_id + "\n" +
                this.speaker_ids;

        return str;
    }
}
