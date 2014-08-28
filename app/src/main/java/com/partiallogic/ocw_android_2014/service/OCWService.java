package com.partiallogic.ocw_android_2014.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.partiallogic.ocw_android_2014.Utility;
import com.partiallogic.ocw_android_2014.net.JsonController;
import com.partiallogic.ocw_android_2014.net.ServiceClient;
import com.partiallogic.ocw_android_2014.obj.Event;
import com.partiallogic.ocw_android_2014.obj.Schedule;
import com.partiallogic.ocw_android_2014.obj.Speaker;
import com.partiallogic.ocw_android_2014.obj.Track;
import com.partiallogic.ocw_android_2014.provider.ProviderContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by markholland on 28/08/14.
 */
public class OCWService extends IntentService {
    
    private static final String LOG_TAG = OCWService.class.getSimpleName();
    
    public OCWService() {
        super("OCWService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Schedule schedule = JsonController.getInstance().getSchedule(ServiceClient.getInstance(),
                this);

        List<Speaker> speakers = JsonController.getInstance().getSpeakers(ServiceClient.getInstance(),
                this);

        List<Track> tracks = JsonController.getInstance().getTracks(ServiceClient.getInstance(),
                this);


        List<Event> events = schedule.getEvents();
        ArrayList<String> scheduleDays = new ArrayList<String>();
        String currentDay;

        Vector<ContentValues> eventsVector = new Vector<ContentValues>(events.size());

        for (Event event : events) {
            ContentValues eventValues = new ContentValues();

            // Get list of the dates that have events.
            currentDay = Utility.getDateFromStartTime(event.getStart_time());
            if (!scheduleDays.contains(currentDay)) {
                scheduleDays.add(currentDay);
            }

            eventValues.put(ProviderContract.EventEntry.COLUMN_EVENT_ID, event.getId());
            eventValues.put(ProviderContract.EventEntry.COLUMN_TITLE, event.getTitle());
            eventValues.put(ProviderContract.EventEntry.COLUMN_DESCRIPTION, event.getDescription());
            eventValues.put(ProviderContract.EventEntry.COLUMN_START_TIME, event.getStart_time());
            eventValues.put(ProviderContract.EventEntry.COLUMN_END_TIME, event.getEnd_time());
            eventValues.put(ProviderContract.EventEntry.COLUMN_ROOM_TITLE, event.getRoom_title());
            eventValues.put(ProviderContract.EventEntry.COLUMN_TRACK_ID, event.getTrack_id());

            eventsVector.add(eventValues);
        }

        if (eventsVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[eventsVector.size()];
            eventsVector.toArray(cvArray);
            int rowsInserted = this.getContentResolver()
                    .bulkInsert(ProviderContract.EventEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of event data");
        }

        Vector<ContentValues> datesVector = new Vector<ContentValues>(scheduleDays.size());

        for(String day : scheduleDays) {
            ContentValues dateValues = new ContentValues();
            dateValues.put(ProviderContract.DatesEntry.COLUMN_DATE, day);

            datesVector.add(dateValues);
        }

        if (datesVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[datesVector.size()];
            datesVector.toArray(cvArray);
            int rowsInserted = this.getContentResolver()
                    .bulkInsert(ProviderContract.DatesEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of date data");
        }

        Vector<ContentValues> speakersVector = new Vector<ContentValues>(speakers.size());

        for (Speaker speaker : speakers) {

            ContentValues speakerValues = new ContentValues();

            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_SPEAKER_ID, speaker.getId());
            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_FULLNAME, speaker.getFullname());
            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_AFFILIATION, speaker.getAffiliation());
            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_BIOGRAPHY, speaker.getBiography());
            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_WEBSITE, speaker.getWebsite());
            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_TWITTER, speaker.getTwitter());
            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_IDENTICA, speaker.getIdentica());
            speakerValues.put(ProviderContract.SpeakerEntry.COLUMN_BLOG_URL, speaker.getBlog_url());

            speakersVector.add(speakerValues);

        }

        if (speakersVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[speakersVector.size()];
            speakersVector.toArray(cvArray);
            int rowsInserted = this.getContentResolver()
                    .bulkInsert(ProviderContract.SpeakerEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of speaker data");
        }

        Vector<ContentValues> speaks_atVector = new Vector<ContentValues>();

        for (Event event : events) {

            if (event.getSpeaker_ids() != null)
                for (String s : event.getSpeaker_ids()) {

                    ContentValues speaksAtValues = new ContentValues();

                    speaksAtValues.put(ProviderContract.SpeaksAtEntry.COLUMN_EVENT_ID, event.getId());
                    speaksAtValues.put(ProviderContract.SpeaksAtEntry.COLUMN_SPEAKER_ID, s);

                    speaks_atVector.add(speaksAtValues);
                }
        }

        if (speaks_atVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[speaks_atVector.size()];
            speaks_atVector.toArray(cvArray);
            int rowsInserted = this.getContentResolver()
                    .bulkInsert(ProviderContract.SpeaksAtEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of speaks_at data");
        }

        Vector<ContentValues> tracksVector = new Vector<ContentValues>(tracks.size());

        for (Track track : tracks) {

            ContentValues trackValues = new ContentValues();

            trackValues.put(ProviderContract.TrackEntry.COLUMN_TRACK_ID, track.getId());
            trackValues.put(ProviderContract.TrackEntry.COLUMN_TITLE, track.getTitle());
            trackValues.put(ProviderContract.TrackEntry.COLUMN_DESCRIPTION, track.getDescription());
            trackValues.put(ProviderContract.TrackEntry.COLUMN_COLOR, track.getStringColor());
            trackValues.put(ProviderContract.TrackEntry.COLUMN_EXCERPT, track.getExcerpt());

            tracksVector.add(trackValues);
        }

        if (tracksVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[tracksVector.size()];
            tracksVector.toArray(cvArray);
            int rowsInserted = this.getContentResolver()
                    .bulkInsert(ProviderContract.TrackEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of track data");
        }

    }

}
