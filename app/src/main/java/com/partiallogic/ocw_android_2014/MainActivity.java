package com.partiallogic.ocw_android_2014;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.partiallogic.ocw_android_2014.provider.ProviderContract;
import com.partiallogic.ocw_android_2014.service.OCWService;


public class MainActivity extends Activity implements ScheduleFragment.Callback
        , LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private boolean mTwoPane;

    private static int NAV_LOADER = 0;
    private static final int MENU_ABOUT = -7;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private SimpleCursorAdapter mDrawerAdapter;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(NAV_LOADER, null, this);

        // When was the server files last modified
        Long lastModified = Long.parseLong(
                Utility.getSharedPrefString(getApplicationContext(),
                        Utility.LAST_MODIFIED, Utility.LAST_MODIFIED_DEFAULT));
        if (lastModified == (Long.parseLong(Utility.LAST_MODIFIED_DEFAULT))) {
            lastModified = System.currentTimeMillis();
            Utility.setSharedPrefString(this,
                    Utility.LAST_MODIFIED, "" + (lastModified + 3600000));
        }

        Log.d(LOG_TAG, "" + lastModified);

        if ((lastModified + 36000000) <=
                System.currentTimeMillis()) {
            //DownloadDataTask dl = new DownloadDataTask(this);
            //dl.execute();
            Intent intent = new Intent(this, OCWService.class);
            this.startService(intent);
            Utility.setSharedPrefString(this,
                    Utility.LAST_MODIFIED, "" + System.currentTimeMillis());
        }

        // Navigation Drawer
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerAdapter = new SimpleCursorAdapter(this,
                R.layout.drawer_list_item,
                null,
                new String[]{ProviderContract.DatesEntry.COLUMN_DATE},
                new int[]{R.id.drawer_list_item_textview},
                0
        );
        mDrawerAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                ((TextView) view).setText(Utility.getNavTime(cursor.getString(columnIndex)));
                return true;
            }
        });
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        View footer = getLayoutInflater().inflate(R.layout.drawer_footer, null);
        mDrawerList.addFooterView(footer);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        // Setting up 2 pane layout
        if (findViewById(R.id.event_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.event_detail_container, new EventDetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    // Schedule list items
    @Override
    public void onItemSelected(String event_id) {
        if (mTwoPane) {

            Bundle args = new Bundle();
            args.putString(EventActivity.EVENT_KEY, event_id);

            EventDetailFragment fragment = new EventDetailFragment();
            fragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .replace(R.id.event_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, EventActivity.class)
                    .putExtra(EventActivity.EVENT_KEY, event_id);
            startActivity(intent);
        }
    }


    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    // Selecting an item in the navigation drawer
    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();

        Cursor c = ((SimpleCursorAdapter)
                ((HeaderViewListAdapter) mDrawerList.getAdapter()).getWrappedAdapter()).getCursor();
        if (c != null) {
            c.moveToPosition(position);
            if (!c.isAfterLast()) {
                String date = c.getString(c.getColumnIndex(ProviderContract.DatesEntry.COLUMN_DATE));
                setTitle(date);

                args.putString(ScheduleFragment.DATE_KEY, date);
                fragment.setArguments(args);
            } else {
                showDialog(MENU_ABOUT);
                return;
            }
        }
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(position, true);

            mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = ProviderContract.DatesEntry.COLUMN_DATE + " ASC";

        return new CursorLoader(
                this,
                ProviderContract.DatesEntry.buildDateUri(),
                new String[]{ProviderContract.DatesEntry._ID,
                        ProviderContract.DatesEntry.COLUMN_DATE},
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mDrawerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDrawerAdapter.swapCursor(null);
    }


    protected Dialog onCreateDialog(int id) {
        if (id == MENU_ABOUT) {
            Context context = getApplicationContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.about, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.about);
            builder.setCancelable(true);
            builder.setView(v);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            final AlertDialog alert = builder.create();
            return alert;
        }
        return null;
    }
}
