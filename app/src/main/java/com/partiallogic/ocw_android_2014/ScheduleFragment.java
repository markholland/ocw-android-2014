package com.partiallogic.ocw_android_2014;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.EventEntry;

/**
 * Created by markholland on 14/08/14.
 */
public class ScheduleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = ScheduleFragment.class.getSimpleName();

    private static final int SCHEDULE_LOADER = 0;

    private static final String[] SCHEDULE_COLUMNS = {
            EventEntry._ID,
            EventEntry.COLUMN_EVENT_ID,
            EventEntry.COLUMN_TITLE,
            EventEntry.COLUMN_START_TIME,
            EventEntry.COLUMN_ROOM_TITLE
    };

    public static final int COL_EVENT_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_START_TIME = 3;
    public static final int COL_ROOM_TITLE = 4;

    private ScheduleAdapter mScheduleAdapter;


    public ScheduleFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SCHEDULE_LOADER, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloadTrackTask downloadTrackTask = new DownloadTrackTask(getActivity());
        downloadTrackTask.execute();
        DownloadScheduleTask schedTask = new DownloadScheduleTask(getActivity());
        schedTask.execute();
        //DownloadSpeakerTask downloadSpeakerTask = new DownloadSpeakerTask(getActivity());
        //downloadSpeakerTask.execute("1");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        mScheduleAdapter = new ScheduleAdapter(
                getActivity(),
                null,
                0
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listview_schedule);
        listView.setAdapter(mScheduleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = mScheduleAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    String eventId = cursor.getString(COL_EVENT_ID);
                    Intent intent = new Intent(getActivity(), EventActivity.class)
                            .putExtra(EventDetailFragment.EVENT_KEY, eventId);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //String selection = EventEntry.COLUMN_TRACK_ID + " =? ";
        //String selectionArgs[] = {"32"};
        String sortOrder = EventEntry.COLUMN_START_TIME + " ASC";

        return new CursorLoader(
                getActivity(),
                EventEntry.buildEventUri(),
                SCHEDULE_COLUMNS,
                null,//selection,
                null,//selectionArgs,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mScheduleAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mScheduleAdapter.swapCursor(null);
    }
}
