package com.partiallogic.ocw_android_2014;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.EventEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.SpeakerEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.TrackEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.DatesEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderDbHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created by markholland on 12/08/14.
 */
public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    public static final String TEST_EVENT_ID = "101";
    public static final String TEST_EVENT_TITLE = "Test Event";
    public static final String TEST_EVENT_DESCRIPTION = "A description of an event";
    public static final String TEST_EVENT_START_TIME = "2014-06-24T10:00:00.000-07:00";
    public static final String TEST_EVENT_END_TIME = "2014-06-24T11:00:00.000-07:00";
    public static final String TEST_ROOM_TITLE = "Testing room";
    public static final String TEST_TRACK_ID = "102";
    public static final String TEST_TRACK_TITLE = "Test Track";
    public static final String TEST_TRACK_DESCRIPTION = "A description of a track";
    public static final String TEST_TRACK_COLOR = "Blue";
    public static final String TEST_TRACK_EXCERPT = "An excerpt of a track description";
    public static final String TEST_SPEAKER_ID = "103";
    public static final String TEST_SPEAKER_FULLNAME = "Mr. A. Speaker";
    public static final String TEST_SPEAKER_AFFILIATION = "Testers inc.";
    public static final String TEST_SPEAKER_BIOGRAPHY = "A biography all about the speaker";
    public static final String TEST_SPEAKER_WEBSITE = "http://www.tester.com";
    public static final String TEST_SPEAKER_TWITTER = "tester";
    public static final String TEST_SPEAKER_IDENTICA = "tester";
    public static final String TEST_SPEAKER_BLOG_URL = "http://www.tester.com/blog";
    public static final String TEST_DATE = "2014-06-24";

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(ProviderDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new ProviderDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {

        ProviderDbHelper dbHelper = new ProviderDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues;
        Cursor cursor;

        long rowId;

        // Track

        testValues = createTrackValues();
        Long trackRowId;
        trackRowId = rowId = db.insert(TrackEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(rowId != -1);
        Log.d(LOG_TAG, "New row id: " + rowId);

        cursor = db.query(
                TrackEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(cursor, testValues);

        // Speaker

        testValues = createSpeakerValues();

        rowId = db.insert(SpeakerEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(rowId != -1);
        Log.d(LOG_TAG, "New row id: " + rowId);

        cursor = db.query(
                SpeakerEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(cursor, testValues);

        // Event

        testValues = createEventValues();

        rowId = db.insert(EventEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(rowId != -1);
        Log.d(LOG_TAG, "New row id: " + rowId);

        cursor = db.query(
                EventEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(cursor, testValues);

        // Date

        testValues = createDateValues();

        rowId = db.insert(DatesEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(rowId != -1);
        Log.d(LOG_TAG, "New row id: " + rowId);

        cursor = db.query(
                DatesEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(cursor, testValues);

        dbHelper.close();

    }

    static ContentValues createEventValues() {
        ContentValues EventValues = new ContentValues();

        EventValues.put(EventEntry.COLUMN_EVENT_ID, TEST_EVENT_ID);
        EventValues.put(EventEntry.COLUMN_TITLE, TEST_EVENT_TITLE);
        EventValues.put(EventEntry.COLUMN_DESCRIPTION, TEST_EVENT_DESCRIPTION);
        EventValues.put(EventEntry.COLUMN_START_TIME, TEST_EVENT_START_TIME);
        EventValues.put(EventEntry.COLUMN_END_TIME, TEST_EVENT_END_TIME);
        EventValues.put(EventEntry.COLUMN_ROOM_TITLE, TEST_ROOM_TITLE);
        EventValues.put(EventEntry.COLUMN_TRACK_ID, TEST_TRACK_ID);
        EventValues.put(EventEntry.COLUMN_SPEAKER_ID, TEST_SPEAKER_ID);

        return EventValues;
    }

    static ContentValues createTrackValues() {
        ContentValues trackValues = new ContentValues();

        trackValues.put(TrackEntry.COLUMN_TRACK_ID, TEST_TRACK_ID);
        trackValues.put(TrackEntry.COLUMN_TITLE, TEST_TRACK_TITLE);
        trackValues.put(TrackEntry.COLUMN_DESCRIPTION, TEST_TRACK_DESCRIPTION);
        trackValues.put(TrackEntry.COLUMN_COLOR, TEST_TRACK_COLOR);
        trackValues.put(TrackEntry.COLUMN_EXCERPT, TEST_TRACK_EXCERPT);

        return trackValues;
    }

    static ContentValues createSpeakerValues() {
        ContentValues speakerValues = new ContentValues();

        speakerValues.put(SpeakerEntry.COLUMN_SPEAKER_ID, TEST_SPEAKER_ID);
        speakerValues.put(SpeakerEntry.COLUMN_FULLNAME, TEST_SPEAKER_FULLNAME);
        speakerValues.put(SpeakerEntry.COLUMN_AFFILIATION, TEST_SPEAKER_AFFILIATION);
        speakerValues.put(SpeakerEntry.COLUMN_BIOGRAPHY, TEST_SPEAKER_BIOGRAPHY);
        speakerValues.put(SpeakerEntry.COLUMN_WEBSITE, TEST_SPEAKER_WEBSITE);
        speakerValues.put(SpeakerEntry.COLUMN_TWITTER, TEST_SPEAKER_TWITTER);
        speakerValues.put(SpeakerEntry.COLUMN_IDENTICA, TEST_SPEAKER_IDENTICA);
        speakerValues.put(SpeakerEntry.COLUMN_BLOG_URL, TEST_SPEAKER_BLOG_URL);

        return speakerValues;
    }

    static ContentValues createDateValues() {
        ContentValues speakerValues = new ContentValues();

        speakerValues.put(DatesEntry.COLUMN_DATE, TEST_DATE);

        return speakerValues;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mContext.deleteDatabase(ProviderDbHelper.DATABASE_NAME);
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();
    }
}

