package com.partiallogic.ocw_android_2014;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.EventEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.SpeakerEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.SpeaksAtEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.TrackEntry;

/**
 * Created by markholland on 20/08/14.
 */
public class EventDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = EventDetailFragment.class.getSimpleName();

    public static final String EVENT_KEY = "event_id";

    private static final int EVENT_LOADER = 0;

    private static final String EVENT_SHARE_HASHTAG = "#OCW";

    private ShareActionProvider mShareActionProvider;
    private String mShareString = null;

    private static final String[] EVENT_COLUMNS = {
            EventEntry.TABLE_NAME + "." + EventEntry._ID,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_TITLE,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_DESCRIPTION,
            EventEntry.COLUMN_ROOM_TITLE,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_TRACK_ID,
            SpeakerEntry.COLUMN_FULLNAME,
            SpeaksAtEntry.TABLE_NAME + "." + SpeakerEntry.COLUMN_SPEAKER_ID,
            TrackEntry.COLUMN_COLOR,
            SpeakerEntry.COLUMN_BIOGRAPHY,
            SpeakerEntry.COLUMN_TWITTER
    };

    public static final int COL_EVENT_TITLE = 1;
    public static final int COL_DESCRIPTION = 2;
    public static final int COL_ROOM_TITLE = 3;
    public static final int COL_TRACK_ID = 4;
    public static final int COL_SPEAKER_FULLNAME = 5;
    public static final int COL_SPEAKER_ID = 6;
    public static final int COL_TRACK_COLOR = 7;
    public static final int COL_SPEAKER_BIO = 8;
    public static final int COL_SPEAKER_TWITTER = 9;

    private TextView mEventTitleView;
    private TextView mEventDescriptionView;
    private TextView mEventRoomView;
    private TextView mEventTrackView;
    private TextView mEventSpeakerView;
    private ImageView mEventSpeakerImageView;
    private LinearLayout mHeader;
    private TextView mSpeakerNameView;
    private TextView mSpeakerBioView;
    private TextView mSpeakerTwitterView;


    public EventDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(EVENT_LOADER, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (mShareString != null) {
            mShareActionProvider.setShareIntent(createShareEventIntent());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, null);
        mEventTitleView = (TextView) rootView.findViewById(R.id.detail_title_textview);
        mEventDescriptionView = (TextView) rootView.findViewById(R.id.detail_description_textview);
        mEventRoomView = (TextView) rootView.findViewById(R.id.detail_room_title_textview);
        mEventTrackView = (TextView) rootView.findViewById(R.id.detail_track_id_textview);
        mEventSpeakerView = (TextView) rootView.findViewById(R.id.detail_speaker_textview);
        //mEventSpeakerImageView = (ImageView) rootView.findViewById(R.id.detail_speaker_imageview);
        mHeader = (LinearLayout) rootView.findViewById(R.id.detail_header);
        mSpeakerNameView = (TextView) rootView.findViewById(R.id.detail_speaker_name_textview);;
        mSpeakerBioView = (TextView) rootView.findViewById(R.id.detail_speaker_bio_textview);;
        mSpeakerTwitterView = (TextView) rootView.findViewById(R.id.detail_speaker_twitter_value_textview);;

        return rootView;
    }

    private Intent createShareEventIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                mShareString + " " + EVENT_SHARE_HASHTAG);
        return shareIntent;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //String selection = ProviderContract.EventEntry.COLUMN_EVENT_ID + " =? ";
        //String selectionArgs[] = {mEventId};
        Log.v(LOG_TAG, "In onCreateLoader");
        Intent intent = getActivity().getIntent();
        if (intent == null || !intent.hasExtra(EVENT_KEY)) {
            return null;
        }
        String eventId = intent.getStringExtra(EVENT_KEY);
        Log.d(LOG_TAG, eventId);

        // Sort order: Ascending, by date.
        String sortOrder = EventEntry.COLUMN_TITLE + " ASC";

        //Uri EventByIdUri = EventEntry.buildEventByIdUri(Long.parseLong(eventId));
        //Log.v(LOG_TAG, EventByIdUri.toString());

        Uri EventByIdUri = SpeaksAtEntry.buildSpeaks_atByEventIdUri(Long.parseLong(eventId));
        Log.v(LOG_TAG, EventByIdUri.toString());

        return new CursorLoader(
                getActivity(),
                EventByIdUri,
                EVENT_COLUMNS,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "In onLoadFinished");
        if(!data.moveToFirst()) { return; }

        String eventTitle = data.getString(COL_EVENT_TITLE);
        mEventTitleView.setText(eventTitle);

        String eventSpeakerName =
                data.getString(COL_SPEAKER_FULLNAME);
        mEventSpeakerView.setText(eventSpeakerName);

        String eventDescription =
                data.getString(COL_DESCRIPTION);
        mEventDescriptionView.setText(eventDescription);

        mSpeakerNameView.setText(eventSpeakerName);

        String speakerBio = data.getString(COL_SPEAKER_BIO);
        mSpeakerBioView.setText(speakerBio);

        String speakerTwitter = data.getString(COL_SPEAKER_TWITTER);
        mSpeakerTwitterView.setText(speakerTwitter);


        String eventRoomTitle =
                data.getString(COL_ROOM_TITLE);
        //mEventRoomView.setText(eventRoomTitle);

        String eventTrackId =
                data.getString(COL_TRACK_ID);
        //mEventTrackView.setText(eventTrackId);


        int trackColor = 0;
        trackColor = data.getInt(COL_TRACK_COLOR);
        if(trackColor == 0) {
            trackColor = 999999999;
        }

        Log.d(LOG_TAG, ""+trackColor);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(trackColor));

        mHeader.setBackgroundColor(trackColor);

        /*
        String speaker_id = data.getString(data.getColumnIndex(SpeakerEntry.COLUMN_SPEAKER_ID));
        //TODO
        String image_url = "http://opensourcebridge.org/system/photos/"
                +speaker_id+"/profile/headshot.jpg";
        DownloadImageTask dl = new DownloadImageTask(mEventSpeakerImageView);
        dl.execute(image_url);
        */

        // We still need this for the share intent
        mShareString = eventTitle;

        Log.v(LOG_TAG, "Event Share String: " + mShareString);

        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareEventIntent());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}

