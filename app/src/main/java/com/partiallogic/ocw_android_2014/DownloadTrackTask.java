package com.partiallogic.ocw_android_2014;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.partiallogic.ocw_android_2014.net.JsonController;
import com.partiallogic.ocw_android_2014.net.ServiceClient;
import com.partiallogic.ocw_android_2014.obj.Track;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.TrackEntry;

import java.util.List;

/**
 * Created by markholland on 13/08/14.
 */
public class DownloadTrackTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = DownloadTrackTask.class.getSimpleName();

    private final Context mContext;

    public DownloadTrackTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        List<Track> tracks = JsonController.getInstance().getTracks(ServiceClient.getInstance(),
                mContext);

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
