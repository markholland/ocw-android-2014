package com.partiallogic.ocw_android_2014;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

    public static final String DATE_KEY = "date";

    private static final String[] SCHEDULE_COLUMNS = {
            EventEntry.TABLE_NAME + "." + EventEntry._ID,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_EVENT_ID,
            EventEntry.COLUMN_TITLE,
            EventEntry.COLUMN_START_TIME,
            EventEntry.COLUMN_ROOM_TITLE,
            EventEntry.TABLE_NAME + "." + EventEntry.COLUMN_TRACK_ID
    };

    public static final int COL_EVENT_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_START_TIME = 3;
    public static final int COL_ROOM_TITLE = 4;
    public static final int COL_TRACK_ID = 5;

    private ScheduleAdapter mScheduleAdapter;
    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String date);
    }

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

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
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

        mListView = (ListView) rootView.findViewById(R.id.listview_schedule);
        mListView.setAdapter(mScheduleAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = mScheduleAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    // Only events with more detail to show
                    if(cursor.getString(COL_TRACK_ID) != null) {
                        ((Callback) getActivity())
                                .onItemSelected(cursor.getString(COL_EVENT_ID));
                    }
                }
                mPosition = position;

            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = EventEntry.COLUMN_START_TIME + " ASC";

        //TODO
        String date = "2014-06-26";

        if(getArguments() != null) {
            date = getArguments().getString(DATE_KEY);
            Log.d(LOG_TAG, date);
            return new CursorLoader(
                    getActivity(),
                    EventEntry.buildEventUri(),
                    SCHEDULE_COLUMNS,
                    EventEntry.COLUMN_START_TIME + " LIKE ?",
                    new String[]{date + "%"},//selectionArgs,
                    sortOrder
            );
        } else {
            getActivity().setTitle("All");
            return new CursorLoader(
                    getActivity(),
                    EventEntry.buildEventUri(),
                    SCHEDULE_COLUMNS,
                    null,
                    null,//selectionArgs,
                    sortOrder
            );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mScheduleAdapter.swapCursor(data);
        if(mPosition != ListView.INVALID_POSITION) {
            mListView.setSelection(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mScheduleAdapter.swapCursor(null);
    }
}
