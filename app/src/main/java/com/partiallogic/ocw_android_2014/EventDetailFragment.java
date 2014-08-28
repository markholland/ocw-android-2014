package com.partiallogic.ocw_android_2014;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
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

    private static final int EVENT_LOADER = 0;

    private static final String EVENT_SHARE_HASHTAG = "#OCW";

    private ShareActionProvider mShareActionProvider;
    private String mShareString = null;

    private static final String[] EVENT_COLUMNS = {
            EventEntry.TABLE_NAME + "." + EventEntry._ID,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_TITLE,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_DESCRIPTION,
            EventEntry.COLUMN_START_TIME,
            EventEntry.COLUMN_END_TIME,
            EventEntry.COLUMN_ROOM_TITLE,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_TRACK_ID,
            SpeakerEntry.COLUMN_FULLNAME,
            SpeaksAtEntry.TABLE_NAME + "." + SpeakerEntry.COLUMN_SPEAKER_ID,
            TrackEntry.COLUMN_COLOR,
            SpeakerEntry.COLUMN_BIOGRAPHY,
            SpeakerEntry.COLUMN_WEBSITE,
            SpeakerEntry.COLUMN_TWITTER,
            SpeakerEntry.COLUMN_IDENTICA,
            SpeakerEntry.COLUMN_BLOG_URL
    };

    public static final int COL_EVENT_TITLE = 1;
    public static final int COL_DESCRIPTION = 2;
    public static final int COL_START_TIME = 3;
    public static final int COL_END_TIME = 4;
    public static final int COL_ROOM_TITLE = 5;
    public static final int COL_TRACK_ID = 6;
    public static final int COL_SPEAKER_FULLNAME = 7;
    public static final int COL_SPEAKER_ID = 8;
    public static final int COL_TRACK_COLOR = 9;
    public static final int COL_SPEAKER_BIO = 10;
    public static final int COL_SPEAKER_WEBSITE = 11;
    public static final int COL_SPEAKER_TWITTER = 12;
    public static final int COL_SPEAKER_IDENTICA = 13;
    public static final int COL_SPEAKER_BLOG_URL = 14;

    private TextView mEventTitleView;
    private TextView mEventDescriptionView;
    private TextView mEventRoomView;
    private TextView mEventTrackView;
    private TextView mEventTimeLocationView;
    private ImageView mEventSpeakerImageView;
    private LinearLayout mHeader;
    private ImageView mFooterImageView;
    private TextView mSpeakerNameView;
    private TextView mSpeakerBioView;
    private TextView mSpeakerWebsiteView;
    private TextView mSpeakerTwitterView;
    private TextView mSpeakerIdenticaView;
    private TextView mSpeakerBlog_urlView;


    public EventDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey(EventActivity.EVENT_KEY)) {
            getLoaderManager().initLoader(EVENT_LOADER, null, this);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider =
                (ShareActionProvider) menuItem.getActionProvider();

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
        mEventTimeLocationView = (TextView) rootView.findViewById(R.id.detail_time_location_textview);
        //mEventSpeakerImageView = (ImageView) rootView.findViewById(R.id.detail_speaker_imageview);
        mHeader = (LinearLayout) rootView.findViewById(R.id.detail_header);
        mFooterImageView = (ImageView) rootView.findViewById(R.id.event_detail_footer);
        mSpeakerNameView = (TextView) rootView.findViewById(R.id.detail_speaker_name_textview);
        mSpeakerBioView = (TextView) rootView.findViewById(R.id.detail_speaker_bio_textview);
        mSpeakerWebsiteView  = (TextView) rootView.findViewById(R.id.detail_speaker_website_textview);
        mSpeakerTwitterView = (TextView) rootView.findViewById(R.id.detail_speaker_twitter_textview);
        mSpeakerIdenticaView = (TextView) rootView.findViewById(R.id.detail_speaker_identica_textview);
        mSpeakerBlog_urlView = (TextView) rootView.findViewById(R.id.detail_speaker_blog_url_textview);

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
        String eventId = getArguments().getString(EventActivity.EVENT_KEY);
        Log.d(LOG_TAG, eventId);

        // Sort order: Ascending, by date.
        String sortOrder = EventEntry.COLUMN_TITLE + " ASC";

        return new CursorLoader(
                getActivity(),
                SpeaksAtEntry.buildSpeaks_atByEventIdUri(Long.parseLong(eventId)),
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

        String eventStartTime = data.getString(COL_START_TIME);
        String eventEndTime = data.getString(COL_END_TIME);
        String eventRoom = data.getString(COL_ROOM_TITLE);

        String eventTimeLocation =
                Utility.getEventDetailTimeLocation(eventStartTime, eventEndTime, eventRoom);
        mEventTimeLocationView.setText(eventTimeLocation);

        String eventDescription =
                data.getString(COL_DESCRIPTION);
        mEventDescriptionView.setText(eventDescription);

        mFooterImageView.setImageResource(R.drawable.ic_icon_footer);

        String eventSpeakerName = data.getString(EventDetailFragment.COL_SPEAKER_FULLNAME);

        mSpeakerNameView.setText(eventSpeakerName);

        String speakerBio = data.getString(COL_SPEAKER_BIO);
        mSpeakerBioView.setText(speakerBio);

        String speakerWebsite = data.getString(COL_SPEAKER_WEBSITE);
        if(!speakerWebsite.equals(""))
            mSpeakerWebsiteView.setText("Website : " +speakerWebsite);
        else
            mSpeakerWebsiteView.setVisibility(View.GONE);

        String speakerTwitter = data.getString(COL_SPEAKER_TWITTER);
        if(!speakerTwitter.equals(""))
            mSpeakerTwitterView.setText("Twitter : " +speakerTwitter);
        else
            mSpeakerTwitterView.setVisibility(View.GONE);

        String speakerIdentica = data.getString(COL_SPEAKER_IDENTICA);
        if(!speakerIdentica.equals(""))
            mSpeakerIdenticaView.setText("Identica : " +speakerIdentica);
        else
            mSpeakerIdenticaView.setVisibility(View.GONE);

        String speakerBlog_url = data.getString(COL_SPEAKER_BLOG_URL);
        if(!speakerBlog_url.equals(""))
            mSpeakerBlog_urlView.setText("Blog Url : " +speakerBlog_url);
        else
            mSpeakerBlog_urlView.setVisibility(View.GONE);

        String eventTrackId =
                data.getString(COL_TRACK_ID);
        //mEventTrackView.setText(eventTrackId);


        int trackColor = 0;
        trackColor = data.getInt(COL_TRACK_COLOR);
        if(trackColor == 0) {
            trackColor = getActivity().getResources().
                    getColor(android.R.color.holo_blue_dark);
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

        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareEventIntent());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}

