package com.partiallogic.ocw_android_2014;

import com.partiallogic.ocw_android_2014.Event;

import java.util.List;

/**
 * Created by markholland on 13/08/14.
 */
public class Schedule {
    
    private List<Event> events;

    public Schedule(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        String str = "";

        for( int i = 0; i < events.size(); i++) {
            str += events.get(i).toString();
        }

        return str;
    }
}
