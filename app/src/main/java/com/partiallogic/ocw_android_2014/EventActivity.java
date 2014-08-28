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

            String event_id = getIntent().getStringExtra(EVENT_KEY);

            Bundle arguments = new Bundle();
            arguments.putString(EventActivity.EVENT_KEY, event_id);

            EventDetailFragment fragment = new EventDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.event_detail_container, fragment)
                    .commit();
        }
    }

}
