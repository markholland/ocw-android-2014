package com.partiallogic.ocw_android_2014;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.test.AndroidTestCase;

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
    }

    public void testInsertReadProvider() {

        ProviderDbHelper dbHelper = new ProviderDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues feedTestValues, articleTestValues, authorTestValues;

        Cursor cursor;

        feedTestValues = TestDb.createTrackValues();
        Uri feedInsertUri = mContext.getContentResolver()
                .insert(TrackEntry.CONTENT_URI, feedTestValues);
        assertTrue(feedInsertUri != null);


        authorTestValues = TestDb.createSpeakerValues();
        Uri authorInsertUri = mContext.getContentResolver()
                .insert(SpeakerEntry.CONTENT_URI, authorTestValues);
        assertTrue(authorInsertUri != null);


        articleTestValues = TestDb.createEventValues();
        Uri articleInsertUri = mContext.getContentResolver()
                .insert(EventEntry.CONTENT_URI, articleTestValues);
        assertTrue(articleInsertUri != null);

        /*
        // Feed articles
        cursor = mContext.getContentResolver().query(
                ArticleEntry.buildArticlesWithFeedIdURI(TEST_FEED_ID),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, articleTestValues);
        } else {
            fail("No data");
        }

        // Feeds
        cursor = mContext.getContentResolver().query(
                FeedEntry.CONTENT_URI,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, feedTestValues);
        } else {
            fail("No data");
        }

        // Articles
        cursor = mContext.getContentResolver().query(
                ArticleEntry.CONTENT_URI,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, articleTestValues);
        } else {
            fail("No data");
        }

        // ArticleById
        cursor = mContext.getContentResolver().query(
                ArticleEntry.buildArticleWithIdURI(Integer.parseInt(TEST_ARTICLE_ID)),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, articleTestValues);
        } else {
            fail("No data");
        }

        // ArticleByDate
        cursor = mContext.getContentResolver().query(
                ArticleEntry.buildArticlesWithDateURI(TEST_ARTICLE_ID),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, articleTestValues);
        } else {
            fail("No data");
        }

        // ArticlesRead
        cursor = mContext.getContentResolver().query(
                ArticleEntry.buildArticlesWithReadURI(TEST_READ),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, articleTestValues);
        } else {
            fail("No data");
        }

        // Author articles
        cursor = mContext.getContentResolver().query(
                ArticleEntry.buildArticlesWithAuthorIdURI(TEST_AUTHOR_ID),  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, articleTestValues);
        } else {
            fail("No data");
        }

        // Authors
        cursor = mContext.getContentResolver().query(
                AuthorEntry.CONTENT_URI,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null  // columns to group by
        );

        if(cursor.moveToFirst()) {
            TestDb.validateCursor(cursor, authorTestValues);
        } else {
            fail("No data");
        }
        */
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

    /*
    public void testUpdateArticle() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestDb.createArticleValues();

        Uri articleUri = mContext.getContentResolver().
                insert(ArticleEntry.CONTENT_URI, values);
        long articleRowId = ContentUris.parseId(articleUri);

        // Verify we got a row back.
        assertTrue(articleRowId != -1);
        Log.d(LOG_TAG, "New row id: " + articleRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(ArticleEntry._ID, articleRowId);
        updatedValues.put(ArticleEntry.COLUMN_TITLE, "Santa's Village");

        int count = mContext.getContentResolver().update(
                ArticleEntry.CONTENT_URI, updatedValues, ArticleEntry._ID + "= ?",
                new String[] { Long.toString(articleRowId)});

        assertEquals(count, 1);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                ArticleEntry.buildArticleWithIdURI(articleRowId),
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null // sort order
        );

        TestDb.validateCursor(cursor, updatedValues);
    }

    public void testUpdateFeed() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestDb.createFeedValues();

        Uri feedUri = mContext.getContentResolver().
                insert(FeedEntry.CONTENT_URI, values);
        long feedRowId = ContentUris.parseId(feedUri);

        // Verify we got a row back.
        assertTrue(feedRowId != -1);
        Log.d(LOG_TAG, "New row id: " + feedRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(FeedEntry._ID, feedRowId);
        updatedValues.put(FeedEntry.COLUMN_NAME, "Santa's Village");

        int count = mContext.getContentResolver().update(
                FeedEntry.CONTENT_URI, updatedValues, FeedEntry._ID + "= ?",
                new String[] { Long.toString(feedRowId)});

        assertEquals(count, 1);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                FeedEntry.buildFeedWithIdUri(feedRowId),
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null // sort order
        );

        TestDb.validateCursor(cursor, updatedValues);
    }

    public void testUpdateAuthor() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestDb.createAuthorValues();

        Uri authorUri = mContext.getContentResolver().
                insert(AuthorEntry.CONTENT_URI, values);
        long authorRowId = ContentUris.parseId(authorUri);

        // Verify we got a row back.
        assertTrue(authorRowId != -1);
        Log.d(LOG_TAG, "New row id: " + authorRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(AuthorEntry._ID, authorRowId);
        updatedValues.put(AuthorEntry.COLUMN_NAME, "Santa's Village");

        int count = mContext.getContentResolver().update(
                AuthorEntry.CONTENT_URI, updatedValues, AuthorEntry._ID + "= ?",
                new String[] { Long.toString(authorRowId)});

        assertEquals(count, 1);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                AuthorEntry.buildAuthorWithIdUri(authorRowId),
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null // sort order
        );

        TestDb.validateCursor(cursor, updatedValues);
    }
    */
    // Make sure we can still delete after adding/updating stuff
    public void testDeleteRecordsAtEnd() {
        deleteAllRecords();
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
        // vnd.android.cursor.item/com.partiallogic.ocw_android_2014.app/Event/1
        assertEquals(EventEntry.CONTENT_ITEM_TYPE, type);

        String testDate = "20140612";
        // content://com.partiallogic.ocw_android_2014.app/event?date=20140612
        type = mContext.getContentResolver().getType(
                EventEntry.buildEventByDateUri(testDate));
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/event?date=20140612
        assertEquals(EventEntry.CONTENT_TYPE, type);

        String testRoom = "room 1";
        // content://com.partiallogic.ocw_android_2014.app/event?room=room 1
        type = mContext.getContentResolver().getType(
                EventEntry.buildEventByRoomUri(testRoom));
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/article?room=room 1
        assertEquals(EventEntry.CONTENT_TYPE, type);

        String testTrack = "Track 1";
        // content://com.partiallogic.ocw_android_2014.app/event?track=Track 1
        type = mContext.getContentResolver().getType(
                EventEntry.buildEventByTrackUri(testTrack));
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/article?track=Track 1
        assertEquals(EventEntry.CONTENT_TYPE, type);

        String testSpeaker = "Speaker 1";
        // content://com.partiallogic.ocw_android_2014.app/event?speaker=Speaker 1
        type = mContext.getContentResolver().getType(
                EventEntry.buildEventBySpeakerUri(testSpeaker));
        // vnd.android.cursor.dir/com.partiallogic.ocw_android_2014.app/article?speaker=Speaker 1
        assertEquals(EventEntry.CONTENT_TYPE, type);

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



    // The target api annotation is needed for the call to keySet -- we wouldn't want
    // to use this in our app, but in a test it's fine to assume a higher target.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void addAllContentValues(ContentValues destination, ContentValues source) {
        for (String key : source.keySet()) {
            destination.put(key, source.getAsString(key));
        }
    }
}
