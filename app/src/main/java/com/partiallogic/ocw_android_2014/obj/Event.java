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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRoom_title() {
        return room_title;
    }

    public void setRoom_title(String room_title) {
        this.room_title = room_title;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public List<String> getSpeaker_ids() {
        return speaker_ids;
    }

    public void setSpeaker_ids(List<String> speaker_ids) {
        this.speaker_ids = speaker_ids;
    }

    public String getSpeaker_idsAsString() {

        String str = "";
        if(speaker_ids != null) {
            for (String s : speaker_ids) {
                str += s + ",";
            }
        }
        return str;
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
