package com.partiallogic.ocw_android_2014;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.partiallogic.ocw_android_2014.net.JsonController;
import com.partiallogic.ocw_android_2014.net.ServiceClient;
import com.partiallogic.ocw_android_2014.obj.Schedule;
import com.partiallogic.ocw_android_2014.obj.Speaker;
import com.partiallogic.ocw_android_2014.obj.Track;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.SpeakerEntry;

import java.util.List;

/**
 * Created by markholland on 13/08/14.
 */
public class ScheduleTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = ScheduleTask.class.getSimpleName();

    private final Context mContext;

    public ScheduleTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        List<Track> tracks = JsonController.getInstance().getTracks(ServiceClient.getInstance(),
                mContext);

        Speaker speaker = JsonController.getInstance().getSpeakerById(ServiceClient.getInstance(),
                mContext, "1");

        ContentValues speakerValues = new ContentValues();

        speakerValues.put(SpeakerEntry.COLUMN_SPEAKER_ID, speaker.getId());
        speakerValues.put(SpeakerEntry.COLUMN_FULLNAME, speaker.getFullname());
        speakerValues.put(SpeakerEntry.COLUMN_AFFILIATION, speaker.getAffiliation());
        speakerValues.put(SpeakerEntry.COLUMN_BIOGRAPHY, speaker.getBiography());
        speakerValues.put(SpeakerEntry.COLUMN_WEBSITE, speaker.getWebsite());
        speakerValues.put(SpeakerEntry.COLUMN_TWITTER, speaker.getTwitter());
        speakerValues.put(SpeakerEntry.COLUMN_IDENTICA, speaker.getIdentica());
        speakerValues.put(SpeakerEntry.COLUMN_BLOG_URL, speaker.getBlog_url());

        Uri inserted = mContext.getContentResolver()
                .insert(SpeakerEntry.CONTENT_URI, speakerValues);

        Log.d(LOG_TAG, inserted.toString());

        Cursor cursor = mContext.getContentResolver().query(
                SpeakerEntry.buildSpeakerByIdUri(Long.parseLong(speaker.getId())),
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            Log.d(LOG_TAG, "Yep");
        }




        Schedule schedule = JsonController.getInstance().getSchedule(ServiceClient.getInstance(),
                mContext);



        return null;
    }
}
