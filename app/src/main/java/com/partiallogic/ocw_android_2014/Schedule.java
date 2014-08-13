package com.partiallogic.ocw_android_2014;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by markholland on 13/08/14.
 */
public class Schedule {

    @SerializedName("items")
    private List<EventItem> eventItems;

    public Schedule(List<EventItem> eventItems) {
        this.eventItems = eventItems;
    }

    public List<EventItem> getEventItems() {
        return eventItems;
    }

    public void setEventItems(List<EventItem> eventItems) {
        this.eventItems = eventItems;
    }
}
