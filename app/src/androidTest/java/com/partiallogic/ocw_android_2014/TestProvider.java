package com.partiallogic.ocw_android_2014;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.test.AndroidTestCase;
import android.util.Log;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.DatesEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.EventEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.SpeakerEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.TrackEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderDbHelper;


/**
 * Created by markholland on 12/08/14.
 */
public class TestProvider extends AndroidTestCase {
    
    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void setUp() {
        deleteAllRecords();
        ProviderDbHelper dbHelper = new ProviderDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }
    
    public void testInsertReadProvider() {

        ContentValues eventTestValues, trackTestValues, speakerTestValues,
                eventSpeakerTrackTestValues, datesTestValues;

        Cursor cursor;

        trackTestValues = TestDb.createTrackValues();
        Uri trackInsertUri = mContext.getContentResolver()
                .insert(TrackEntry.CONTENT_URI, trackTestValues);
        assertTrue(trackInsertUri != null);
        assertEquals(Long.parseLong(TestDb.TEST_TRACK_ID),ContentUris.parseId(trackInsertUri));


        speakerTestValues = TestDb.createSpeakerValues();
        Uri speakerInsertUri = mContext.getContentResolver()
                .insert(SpeakerEntry.CONTENT_URI, speakerTestValues);
        assertTrue(speakerInsertUri != null);
        assertEquals(Long.parseLong(TestDb.TEST_SPEAKER_ID), ContentUris.parseId(speakerInsertUri));


        eventTestValues = TestDb.createEventValues();
        Uri eventInsertUri = mContext.getContentResolver()
                .insert(EventEntry.CONTENT_URI, eventTestValues);
        assertTrue(eventInsertUri != null);
        assertEquals(Long.parseLong(TestDb.TEST_EVENT_ID), ContentUris.parseId(eventInsertUri));

        datesTestValues = TestDb.createDateValues();
        Uri dateInsertUri = mContext.getContentResolver()
                .insert(DatesEntry.CONTENT_URI, datesTestValues);
        assertTrue(dateInsertUri != null);
        assertEquals(TestDb.TEST_DATE, dateInsertUri.getPathSegments().get(1));


        // Events
        cursor = mContext.getContentResolver().query(
                EventEntry.buildEventUri(),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, eventTestValues);
        } else {
            fail("No data");
        }
        
        // Event by Id
        cursor = mContext.getContentResolver().query(
                EventEntry.buildEventByIdUri(Integer.parseInt(TestDb.TEST_EVENT_ID)),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, eventTestValues);
        } else {
            fail("No data");
        }

        // Tracks
        cursor = mContext.getContentResolver().query(
                TrackEntry.CONTENT_URI,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, trackTestValues);
        } else {
            fail("No data");
        }

        // Track by Id
        cursor = mContext.getContentResolver().query(
                TrackEntry.buildTrackByIdUri(Integer.parseInt(TestDb.TEST_TRACK_ID)),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, trackTestValues);
        } else {
            fail("No data");
        }

        // Speakers
        cursor = mContext.getContentResolver().query(
                SpeakerEntry.CONTENT_URI,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, speakerTestValues);
        } else {
            fail("No data");
        }

        // Speaker by Id
        cursor = mContext.getContentResolver().query(
                SpeakerEntry.buildSpeakerByIdUri(Integer.parseInt(TestDb.TEST_SPEAKER_ID)),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, speakerTestValues);
        } else {
            fail("No data");
        }

        // date
        cursor = mContext.getContentResolver().query(
                DatesEntry.buildDateUri(),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, datesTestValues);
        } else {
            fail("No data");
        }

    }
    
    
    //brings our database to an empty state
    public void deleteAllRecords() {
        // null selection deletes all rows
        mContext.getContentResolver().delete(
                EventEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                TrackEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                SpeakerEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                EventEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                TrackEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                SpeakerEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();
    }
    
    
    public void testUpdateEvent() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestDb.createEventValues();

        Uri eventUri = mContext.getContentResolver().
                insert(EventEntry.CONTENT_URI, values);
        long eventRowId = ContentUris.parseId(eventUri);

        // Verify we got a row back.
        assertTrue(eventRowId != -1);
        Log.d(LOG_TAG, "New row id: " + eventRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(EventEntry.COLUMN_EVENT_ID, eventRowId);
        updatedValues.put(EventEntry.COLUMN_TITLE, "Santa's Event");

        int count = mContext.getContentResolver().update(
                EventEntry.CONTENT_URI, updatedValues, EventEntry.COLUMN_EVENT_ID + "= ?",
                new String[] { Long.toString(eventRowId)});

        assertEquals(1, count);

        Cursor cursor = mContext.getContentResolver().query(
                EventEntry.buildEventByIdUri(eventRowId),
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null // sort order
        );

        TestDb.validateCursor(cursor, updatedValues);
    }

    public void testUpdateTrack() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestDb.createTrackValues();

        Uri trackUri = mContext.getContentResolver().
                insert(TrackEntry.CONTENT_URI, values);
        long trackRowId = ContentUris.parseId(trackUri);

        // Verify we got a row back.
        assertTrue(trackRowId != -1);
        Log.d(LOG_TAG, "New row id: " + trackRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(TrackEntry.COLUMN_TRACK_ID, trackRowId);
        updatedValues.put(TrackEntry.COLUMN_TITLE, "Santa's Track");

        int count = mContext.getContentResolver().update(
                TrackEntry.CONTENT_URI, updatedValues, TrackEntry.COLUMN_TRACK_ID + "= ?",
                new String[] { Long.toString(trackRowId)});

        assertEquals(1, count);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                TrackEntry.buildTrackByIdUri(trackRowId),
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null // sort order
        );

        TestDb.validateCursor(cursor, updatedValues);
    }

    public void testUpdateSpeaker() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestDb.createSpeakerValues();

        Uri speakerUri = mContext.getContentResolver().
                insert(SpeakerEntry.CONTENT_URI, values);
        long speakerRowId = ContentUris.parseId(speakerUri);

        // Verify we got a row back.
        assertTrue(speakerRowId != -1);
        Log.d(LOG_TAG, "New row id: " + speakerRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(SpeakerEntry.COLUMN_SPEAKER_ID, speakerRowId);
        updatedValues.put(SpeakerEntry.COLUMN_FULLNAME, "Santa");

        int count = mContext.getContentResolver().update(
                SpeakerEntry.CONTENT_URI, updatedValues, SpeakerEntry.COLUMN_SPEAKER_ID + "= ?",
                new String[] { Long.toString(speakerRowId)});

        assertEquals(1, count);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                SpeakerEntry.buildSpeakerByIdUri(speakerRowId),
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null // sort order
        );

        TestDb.validateCursor(cursor, updatedValues);
    }
    
    // Make sure we can still delete after adding/updating stuff
    public void testDeleteRecordsAtEnd() {
        //deleteAllRecords();
    }

    public void testGetType() {
        // content://com.partiallogic.ocw_android_2014.app/event
        String type = mContext.getContentResolver().getType(EventEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/event
        assertEquals(EventEntry.CONTENT_TYPE, type);

        Long testEventId = 1l;
        // content://com.partiallogic.ocw_android_2014.app/event/1
        type = mContext.getContentResolver().getType(
                EventEntry.buildEventByIdUri(testEventId));
        // vnd.android.cursor.item/com.partiallogic.ocw_android_2014.app/event/1
        assertEquals(EventEntry.CONTENT_ITEM_TYPE, type);

        // content://com.partiallogic.ocw_android_2014.app/track
        type = mContext.getContentResolver().getType(
                TrackEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/track
        assertEquals(TrackEntry.CONTENT_TYPE, type);

        Long testTrackId = 1l;
        // content://com.partiallogic.ocw_android_2014.app/track/1
        type = mContext.getContentResolver().getType(
               TrackEntry.buildTrackByIdUri(testTrackId));
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/track/1
        assertEquals(TrackEntry.CONTENT_ITEM_TYPE, type);

        // content://com.partiallogic.ocw_android_2014.app/speaker
        type = mContext.getContentResolver().getType(
                SpeakerEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/speaker
        assertEquals(SpeakerEntry.CONTENT_TYPE, type);

        Long testSpeakerId = 1l;
        // content://com.partiallogic.ocw_android_2014.app/speaker/1
        type = mContext.getContentResolver().getType(
                SpeakerEntry.buildSpeakerByIdUri(testSpeakerId));
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/speaker/1
        assertEquals(SpeakerEntry.CONTENT_ITEM_TYPE, type);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        deleteAllRecords();
    }

    // The target api annotation is needed for the call to keySet -- we wouldn't want
    // to use this in our app, but in a test it's fine to assume a higher target.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void addAllContentValues(ContentValues destination, ContentValues source) {
        for (String key : source.keySet()) {
            destination.put(key, source.getAsString(key));
        }
    }
}
