package com.partiallogic.ocw_android_2014.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.*;

/**
 * Created by markholland on 12/08/14.
 */
public class ScheduleProvider extends ContentProvider{

    public static final String LOG_TAG = ScheduleProvider.class.getSimpleName();

    private static final int EVENT = 100;
    private static final int EVENT_WITH_ID = 101;
    private static final int EVENT_WITH_DATE = 102;
    private static final int EVENT_WITH_ROOM = 103;
    private static final int EVENT_WITH_TRACK = 104;
    private static final int EVENT_WITH_SPEAKER = 105;
    private static final int TRACK = 200;
    private static final int TRACK_WITH_ID = 201;
    private static final int SPEAKER = 300;
    private static final int SPEAKER_WITH_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private ProviderDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ProviderContract.CONTENT_AUTHORITY;

        // ORDER ADDING TO URI MATCHER IS IMPORTANT
        matcher.addURI(authority, ProviderContract.PATH_EVENT, EVENT);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/#", EVENT_WITH_ID);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/*", EVENT_WITH_DATE);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/" +
                EventEntry.COLUMN_ROOM_TITLE + "/*", EVENT_WITH_ROOM);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/" +
                EventEntry.COLUMN_TRACK_ID + "/*", EVENT_WITH_TRACK);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/" +
                EventEntry.COLUMN_SPEAKER_IDS + "/*", EVENT_WITH_SPEAKER);

        matcher.addURI(authority, ProviderContract.PATH_TRACK, TRACK);
        matcher.addURI(authority, ProviderContract.PATH_TRACK + "/#", TRACK_WITH_ID);

        matcher.addURI(authority, ProviderContract.PATH_SPEAKER, SPEAKER);
        matcher.addURI(authority, ProviderContract.PATH_SPEAKER + "/#", SPEAKER_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ProviderDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        Log.d(LOG_TAG, uri.toString());

        //TODO
        retCursor = null;


        switch(sUriMatcher.match(uri)) {
            case EVENT:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.EventEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_WITH_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.EventEntry.TABLE_NAME,
                        projection,
                        ProviderContract.EventEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_WITH_DATE:

                break;
            case EVENT_WITH_ROOM:

                break;
            case EVENT_WITH_TRACK:

                break;
            case EVENT_WITH_SPEAKER:

                break;
            case TRACK:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.TrackEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TRACK_WITH_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.TrackEntry.TABLE_NAME,
                        projection,
                        ProviderContract.TrackEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SPEAKER:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.SpeakerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SPEAKER_WITH_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.SpeakerEntry.TABLE_NAME,
                        projection,
                        ProviderContract.SpeakerEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        Log.d(LOG_TAG, uri.toString());
        switch (match) {
            case EVENT:
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_ID:
                return EventEntry.CONTENT_ITEM_TYPE;
            case EVENT_WITH_DATE:
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_ROOM:
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_TRACK:
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_SPEAKER:
                return EventEntry.CONTENT_TYPE;
            case TRACK:
                return TrackEntry.CONTENT_TYPE;
            case TRACK_WITH_ID:
                return TrackEntry.CONTENT_ITEM_TYPE;
            case SPEAKER:
                return SpeakerEntry.CONTENT_TYPE;
            case SPEAKER_WITH_ID:
                return SpeakerEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case EVENT: {
                long _id = db.insert(EventEntry.TABLE_NAME, null, values);
                if ( _id > 0 ) {
                    returnUri = EventEntry.buildEventByIdUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TRACK: {
                long _id = db.insert(TrackEntry.TABLE_NAME, null, values);
                if ( _id > 0 ) {
                    returnUri = TrackEntry.buildTrackByIdUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case SPEAKER: {
                long _id = db.insert(SpeakerEntry.TABLE_NAME, null, values);
                if ( _id > 0 ) {
                    returnUri = SpeakerEntry.buildSpeakerByIdUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENT:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ProviderContract.EventEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch(match) {
            case EVENT:
                rowsDeleted = db.delete(
                        ProviderContract.EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TRACK:
                rowsDeleted = db.delete(
                        ProviderContract.TrackEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case SPEAKER:
                rowsDeleted = db.delete(
                        ProviderContract.SpeakerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case EVENT:
                rowsUpdated = db.update(ProviderContract.EventEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TRACK:
                rowsUpdated = db.update(ProviderContract.TrackEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case SPEAKER:
                rowsUpdated = db.update(ProviderContract.SpeakerEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
