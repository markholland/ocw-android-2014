package com.partiallogic.ocw_android_2014;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by markholland on 20/08/14.
 */
public class ScheduleAdapter extends CursorAdapter {

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

        int trackColor = cursor.getInt(ScheduleFragment.COL_TRACK_COLOR);

        viewholder.timeView.setBackgroundColor(trackColor);

    }

    public static class ViewHolder {
        public final TextView timeView;
        public final TextView titleView;
        public final TextView roomView;

        public ViewHolder(View view) {
            timeView = (TextView) view.findViewById(R.id.list_item_time_textview);
            titleView = (TextView) view.findViewById(R.id.list_item_title_textview);
            roomView = (TextView) view.findViewById(R.id.list_item_room_textview);
        }
    }
}
