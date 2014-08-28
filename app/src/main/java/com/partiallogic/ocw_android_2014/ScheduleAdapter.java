package com.partiallogic.ocw_android_2014;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.partiallogic.ocw_android_2014.provider.ProviderContract;

import java.util.Date;

/**
 * Created by markholland on 20/08/14.
 */
public class ScheduleAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_WITHOUT_DATE = 0;
    private static final int VIEW_TYPE_WITH_DATE = 1;
    private static final int VIEW_TYPE_COUNT = 2;

    private static Date mTime;

    private static final String LOG_TAG = ScheduleAdapter.class.getSimpleName();

    public ScheduleAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.list_item_schedule;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewholder = (ViewHolder) view.getTag();

        // Read event time from cursor
        String eventTime = cursor.getString(ScheduleFragment.COL_START_TIME);
        // Find TextView and set formatted time on it
        viewholder.timeView.setText(Utility.getHumanStartTime(eventTime));

        // Read event title from cursor
        String titleString = cursor.getString(ScheduleFragment.COL_TITLE);
        // Find TextView and set event title on it
        viewholder.titleView.setText(titleString);

        // Read event room from cursor
        String roomString = cursor.getString(ScheduleFragment.COL_ROOM_TITLE);
        // Find TextView and set event room on it
        if(roomString != null) {
            viewholder.roomView.setText(roomString);
        } else {
            viewholder.roomView.setText("");
        }


        Long trackId = cursor.getLong(ScheduleFragment.COL_TRACK_ID);
        int trackColor = context.getResources().
                getColor(android.R.color.holo_blue_dark); // Color for events without an assigned track
        String trackName = "";

        // Is a valid event with an assigned track
        if(trackId != 0) {
            Cursor c = context.getContentResolver().query(
                    ProviderContract.TrackEntry.buildTrackByIdUri(trackId),
                    null,
                    null,
                    null,
                    null
             );
            if(c.moveToFirst()) {
                trackColor = c.getInt(c.getColumnIndex(ProviderContract.TrackEntry.COLUMN_COLOR));
                trackName = c.getString(c.getColumnIndex(ProviderContract.TrackEntry.COLUMN_TITLE));
            }
            c.close();
        } else {
            view.setEnabled(false);
            view.setOnClickListener(null);
        }
        viewholder.listItemHeader.setBackgroundColor(trackColor);
        viewholder.trackView.setText(trackName);

    }

    public static class ViewHolder {
        public final LinearLayout listItemHeader;
        public final TextView timeView;
        public final TextView trackView;
        public final TextView titleView;
        public final TextView roomView;

        public ViewHolder(View view) {
            listItemHeader = (LinearLayout) view.findViewById(R.id.list_item_header);
            timeView = (TextView) view.findViewById(R.id.list_item_time_textview);
            trackView = (TextView) view.findViewById(R.id.list_item_track_textview);
            titleView = (TextView) view.findViewById(R.id.list_item_title_textview);
            roomView = (TextView) view.findViewById(R.id.list_item_room_textview);
        }
    }
}
