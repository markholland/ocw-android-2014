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

import java.util.ArrayList;
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
        //ArrayList<String> speakerIds = new ArrayList<String>();
        ArrayList<String> scheduleDays = new ArrayList<String>();
        String currentDay;

        Vector<ContentValues> eventsVector = new Vector<ContentValues>(events.size());

        for( Event event : events ) {
            ContentValues eventValues = new ContentValues();

            /*
            // Create list of all speaker ids in schedule
            if(event.getSpeaker_ids() != null) {
                for (String sIds : event.getSpeaker_ids()) {
                    speakerIds.add(sIds);
                }
            }
            */

            // Get list of the dates that have events.
            currentDay = Utility.getDateFromStartTime(event.getStart_time());
            if(!scheduleDays.contains(currentDay)) {
                scheduleDays.add(currentDay);
            }

            eventValues.put(EventEntry.COLUMN_EVENT_ID, event.getId());
            eventValues.put(EventEntry.COLUMN_TITLE, event.getTitle());
            eventValues.put(EventEntry.COLUMN_DESCRIPTION, event.getDescription());
            eventValues.put(EventEntry.COLUMN_START_TIME, event.getStart_time());
            eventValues.put(EventEntry.COLUMN_END_TIME, event.getEnd_time());
            eventValues.put(EventEntry.COLUMN_ROOM_TITLE, event.getRoom_title());
            eventValues.put(EventEntry.COLUMN_TRACK_ID, event.getTrack_id());
            eventValues.put(EventEntry.COLUMN_SPEAKER_ID, event.getSpeaker_idsAsString());

            eventsVector.add(eventValues);
        }

        if (eventsVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[eventsVector.size()];
            eventsVector.toArray(cvArray);
            int rowsInserted = mContext.getContentResolver()
                    .bulkInsert(EventEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of event data");
        }

        /*
        ArrayList<Speaker> speakers = new ArrayList<Speaker>();
        // Now get all the speakers and insert
        for(String sId : speakerIds) {
            speakers.add(JsonController.getInstance().getSpeakerById(ServiceClient.getInstance(),
                    mContext, sId));
        }

        Vector<ContentValues> speakersVector = new Vector<ContentValues>(events.size());

        for(Speaker speaker : speakers) {

            ContentValues speakerValues = new ContentValues();

            speakerValues.put(SpeakerEntry.COLUMN_SPEAKER_ID, speaker.getId());
            speakerValues.put(SpeakerEntry.COLUMN_FULLNAME, speaker.getFullname());
            speakerValues.put(SpeakerEntry.COLUMN_AFFILIATION, speaker.getAffiliation());
            speakerValues.put(SpeakerEntry.COLUMN_BIOGRAPHY, speaker.getBiography());
            speakerValues.put(SpeakerEntry.COLUMN_WEBSITE, speaker.getWebsite());
            speakerValues.put(SpeakerEntry.COLUMN_TWITTER, speaker.getTwitter());
            speakerValues.put(SpeakerEntry.COLUMN_IDENTICA, speaker.getIdentica());
            speakerValues.put(SpeakerEntry.COLUMN_BLOG_URL, speaker.getBlog_url());

            speakersVector.add(speakerValues);
        }

        if (speakersVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[speakersVector.size()];
            speakersVector.toArray(cvArray);
            int rowsInserted = mContext.getContentResolver()
                    .bulkInsert(SpeakerEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of speaker data");
        }
        */

        return null;
    }
}
