package com.partiallogic.ocw_android_2014;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by markholland on 16/08/14.
 */
public class EventDetailFragment extends Fragment {

    private static final String LOG_TAG = EventDetailFragment.class.getSimpleName();

    public EventDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, null);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String title = intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView eventTitle = (TextView) rootView.findViewById(R.id.EventDetailTitleTextView);
            eventTitle.setText(title);
        }

        return rootView;
    }
}