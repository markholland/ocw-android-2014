package com.partiallogic.ocw_android_2014;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.partiallogic.ocw_android_2014.net.JsonController;
import com.partiallogic.ocw_android_2014.net.ServiceClient;
import com.partiallogic.ocw_android_2014.obj.Event;
import com.partiallogic.ocw_android_2014.obj.Schedule;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.EventEntry;

import java.util.List;
import java.util.Vector;

/**
 * Created by markholland on 13/08/14.
 */
public class DownloadScheduleTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = DownloadScheduleTask.class.getSimpleName();

    private final Context mContext;

    public DownloadScheduleTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Schedule schedule = JsonController.getInstance().getSchedule(ServiceClient.getInstance(),
                mContext);

        List<Event> events = schedule.getEvents();

        Vector<ContentValues> cVVector = new Vector<ContentValues>(events.size());

        for( Event event : events ) {
            ContentValues eventValues = new ContentValues();

            eventValues.put(EventEntry.COLUMN_EVENT_ID, event.getId());
            eventValues.put(EventEntry.COLUMN_TITLE, event.getTitle());
            eventValues.put(EventEntry.COLUMN_DESCRIPTION, event.getDescription());
            eventValues.put(EventEntry.COLUMN_START_TIME, event.getStart_time());
            eventValues.put(EventEntry.COLUMN_END_TIME, event.getEnd_time());
            eventValues.put(EventEntry.COLUMN_ROOM_TITLE, event.getTrack_id());
            eventValues.put(EventEntry.COLUMN_SPEAKER_ID, event.getSpeaker_idsAsString());

            cVVector.add(eventValues);

        }

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            int rowsInserted = mContext.getContentResolver()
                    .bulkInsert(EventEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of event data");
        }

        return null;
    }
}
