package com.partiallogic.ocw_android_2014.obj;

import com.google.gson.annotations.SerializedName;

/**
 * Created by markholland on 13/08/14.
 */
public class EventItem {

    @SerializedName("schedule_item")
    private Event scheduleItem;
    @SerializedName("proposal")
    private Event proposalItem;

    public Event getEvent() {
        if(scheduleItem == null) {
            return proposalItem;
        } else {
            return scheduleItem;
        }
    }
}
