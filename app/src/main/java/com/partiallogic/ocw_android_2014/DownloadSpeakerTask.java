package com.partiallogic.ocw_android_2014;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.partiallogic.ocw_android_2014.net.JsonController;
import com.partiallogic.ocw_android_2014.net.ServiceClient;
import com.partiallogic.ocw_android_2014.obj.Speaker;
import com.partiallogic.ocw_android_2014.provider.ProviderContract;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.SpeakerEntry;

import java.util.List;
import java.util.Vector;

/**
 * Created by markholland on 13/08/14.
 */
public class DownloadSpeakerTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = DownloadSpeakerTask.class.getSimpleName();

    private final Context mContext;

    public DownloadSpeakerTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        List<Speaker> speakers = JsonController.getInstance().getSpeakers(ServiceClient.getInstance(),
                mContext);

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
                    .bulkInsert(ProviderContract.SpeakerEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of speaker data");
        }

        return null;
    }
}
