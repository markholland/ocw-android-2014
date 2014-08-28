package com.partiallogic.ocw_android_2014;

import android.os.Bundle;

import com.partiallogic.ocw_android_2014.swipeback.SwipeBackActivity;

public class EventActivity extends SwipeBackActivity {

    public static final String EVENT_KEY = "event_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        if (savedInstanceState == null) {

            // Get the id of the clicked event
            String event_id = getIntent().getStringExtra(EVENT_KEY);

            // Pass it to the fragment
            Bundle arguments = new Bundle();
            arguments.putString(EventActivity.EVENT_KEY, event_id);

            EventDetailFragment fragment = new EventDetailFragment();
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .add(R.id.event_detail_container, fragment)
                    .commit();
        }
    }

}
