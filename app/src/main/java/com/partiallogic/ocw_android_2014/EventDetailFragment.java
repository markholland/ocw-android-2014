package com.partiallogic.ocw_android_2014;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.*;

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
            EventEntry._ID,
            EventEntry.COLUMN_TITLE,
            EventEntry.COLUMN_DESCRIPTION,
            EventEntry.COLUMN_ROOM_TITLE,
            EventEntry.COLUMN_TRACK_ID,
            //EventEntry.COLUMN_SPEAKER_ID
    };

    private TextView mEventTitleView;
    private TextView mEventDescriptionView;
    private TextView mEventRoomView;
    private TextView mEventTrackView;


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
        mEventTitleView = (TextView) getView().findViewById(R.id.detail_title_textview);
        mEventDescriptionView = (TextView) getView().findViewById(R.id.detail_description_textview);
        mEventRoomView = (TextView) getView().findViewById(R.id.detail_room_title_textview);
        mEventTrackView = (TextView) getView().findViewById(R.id.detail_track_id_textview);

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

        Uri EventByIdUri = EventEntry.buildEventByIdUri(
                Long.parseLong(eventId));
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

        String eventTitle = data.getString(data.getColumnIndex(EventEntry.COLUMN_TITLE));
        mEventTitleView.setText(eventTitle);

        String eventDescription =
                data.getString(data.getColumnIndex(EventEntry.COLUMN_DESCRIPTION));
        mEventDescriptionView.setText(eventDescription);

        String eventRoomTitle =
                data.getString(data.getColumnIndex(EventEntry.COLUMN_ROOM_TITLE));
        mEventRoomView.setText(eventRoomTitle);

        String eventTrackId =
                data.getString(data.getColumnIndex(EventEntry.COLUMN_TRACK_ID));
        mEventTrackView.setText(eventTrackId);

        /*
            String speakerId = data.getString(
                    data.getColumnIndex(EventEntry.COLUMN_SPEAKER_ID));

            // If returns a valid speakerId
            if(speakerId.length() > 0) {
                // Speaker by Id
                Cursor cursor = getActivity().getContentResolver().query(
                        SpeakerEntry.buildSpeakerByIdUri(
                                Long.parseLong(speakerId)),
                        null,
                        null, // Columns for the "where" clause
                        null, // Values for the "where" clause
                        null  // columns to group by
                );

                if (cursor.moveToFirst()) {
                    String eventSpeakerId = cursor.getString(2);

                    ((TextView) getView().findViewById(R.id.detail_speaker_textview))
                            .setText(eventSpeakerId);
                } else { // Not in db so download

                    // Load speaker
                    DownloadSpeakerTask dl = new DownloadSpeakerTask(getActivity());
                    dl.execute(speakerId);

                    // Speaker by Id
                    cursor = getActivity().getContentResolver().query(
                            SpeakerEntry.buildSpeakerByIdUri(
                                    Long.parseLong(speakerId)),
                            null,
                            null, // Columns for the "where" clause
                            null, // Values for the "where" clause
                            null  // columns to group by
                    );

                    if (cursor.moveToFirst()) {
                        String eventSpeakerId = cursor.getString(2);

                        ((TextView) getView().findViewById(R.id.detail_speaker_textview))
                                .setText(eventSpeakerId);
                    }
                }
            }
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

