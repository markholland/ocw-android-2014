package com.partiallogic.ocw_android_2014;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends ActionBarActivity implements ScheduleFragment.Callback {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        DownloadTrackTask downloadTrackTask = new DownloadTrackTask(this);
        downloadTrackTask.execute();
        DownloadScheduleTask schedTask = new DownloadScheduleTask(this);
        schedTask.execute();
        DownloadSpeakerTask downloadSpeakerTask = new DownloadSpeakerTask(this);
        downloadSpeakerTask.execute();
        */

        //DownloadDataTask dl = new DownloadDataTask(this);
        //dl.execute();

        if(findViewById(R.id.event_detail_container) != null) {
            mTwoPane = true;

            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.event_detail_container, new EventDetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String event_id) {
        if(mTwoPane) {

            Bundle args = new Bundle();
            args.putString(EventActivity.EVENT_KEY, event_id);

            EventDetailFragment fragment = new EventDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.event_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, EventActivity.class)
                    .putExtra(EventActivity.EVENT_KEY, event_id);
            startActivity(intent);
        }
    }


}
