package com.partiallogic.ocw_android_2014;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.partiallogic.ocw_android_2014.net.JsonController;
import com.partiallogic.ocw_android_2014.net.ServiceClient;
import com.partiallogic.ocw_android_2014.obj.Event;
import com.partiallogic.ocw_android_2014.obj.Schedule;
import com.partiallogic.ocw_android_2014.obj.Speaker;
import com.partiallogic.ocw_android_2014.obj.Track;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by markholland on 21/08/14.
 */
public class DownloadDataTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = DownloadScheduleTask.class.getSimpleName();

    private final Context mContext;

    public DownloadDataTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Schedule schedule = JsonController.getInstance().getSchedule(ServiceClient.getInstance(),
                mContext);

        List<Speaker> speakers = JsonController.getInstance().getSpeakers(ServiceClient.getInstance(),
                mContext);

        List<Track> tracks = JsonController.getInstance().getTracks(ServiceClient.getInstance(),
                mContext);


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

            eventValues.put(EventEntry.COLUMN_EVENT_ID, event.getId());
            eventValues.put(EventEntry.COLUMN_TITLE, event.getTitle());
            eventValues.put(EventEntry.COLUMN_DESCRIPTION, event.getDescription());
            eventValues.put(EventEntry.COLUMN_START_TIME, event.getStart_time());
            eventValues.put(EventEntry.COLUMN_END_TIME, event.getEnd_time());
            eventValues.put(EventEntry.COLUMN_ROOM_TITLE, event.getRoom_title());
            eventValues.put(EventEntry.COLUMN_TRACK_ID, event.getTrack_id());

            eventsVector.add(eventValues);
        }

        if (eventsVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[eventsVector.size()];
            eventsVector.toArray(cvArray);
            int rowsInserted = mContext.getContentResolver()
                    .bulkInsert(EventEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of event data");
        }

        Vector<ContentValues> speakersVector = new Vector<ContentValues>(speakers.size());

        for (Speaker speaker : speakers) {

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

        for (Event event : events) {

            if (event.getSpeaker_ids() != null)
                for (String s : event.getSpeaker_ids()) {

                    ContentValues speaksAtValues = new ContentValues();

                    speaksAtValues.put(SpeaksAtEntry.COLUMN_EVENT_ID, event.getId());
                    speaksAtValues.put(SpeaksAtEntry.COLUMN_SPEAKER_ID, s);

                    Uri inserted = mContext.getContentResolver()
                            .insert(SpeaksAtEntry.CONTENT_URI, speaksAtValues);

                    Log.d(LOG_TAG, inserted.toString());

                }

        }

        for (Track track : tracks) {

            ContentValues trackValues = new ContentValues();

            trackValues.put(TrackEntry.COLUMN_TRACK_ID, track.getId());
            trackValues.put(TrackEntry.COLUMN_TITLE, track.getTitle());
            trackValues.put(TrackEntry.COLUMN_DESCRIPTION, track.getDescription());
            trackValues.put(TrackEntry.COLUMN_COLOR, track.getStringColor());
            trackValues.put(TrackEntry.COLUMN_EXCERPT, track.getExcerpt());

            Uri inserted = mContext.getContentResolver()
                    .insert(TrackEntry.CONTENT_URI, trackValues);

            //Log.d(LOG_TAG, inserted.toString());

        }


        return null;
    }
}