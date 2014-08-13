package com.partiallogic.ocw_android_2014;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.EventEntry;

/**
 * Created by markholland on 14/08/14.
 */
public class ScheduleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int SCHEDULE_LOADER = 0;

    private static final String[] SCHEDULE_COLUMNS = {
            EventEntry._ID,
            EventEntry.COLUMN_TITLE
    };

    public static final int COL_EVENT_TITLE = 0;

    private SimpleCursorAdapter mScheduleAdapter;


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
        DownloadSpeakerTask downloadSpeakerTask = new DownloadSpeakerTask(getActivity());
        downloadSpeakerTask.execute("1");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        mScheduleAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_schedule,
                null,
                new String[]{EventEntry.COLUMN_TITLE
                },
                new int[]{R.id.list_item_title_textview
                },
                0
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listview_schedule);
        listView.setAdapter(mScheduleAdapter);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        return new CursorLoader(
                getActivity(),
                EventEntry.buildEventUri(),
                SCHEDULE_COLUMNS,
                null,
                null,
                null
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
