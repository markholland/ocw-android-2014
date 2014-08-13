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
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/" +
                EventEntry.ON_DATE + "/*", EVENT_WITH_DATE);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/" +
                EventEntry.IN_ROOM + "/*", EVENT_WITH_ROOM);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/" +
                EventEntry.OF_TRACK + "/*", EVENT_WITH_TRACK);
        matcher.addURI(authority, ProviderContract.PATH_EVENT + "/" +
                EventEntry.BY_SPEAKER + "/*", EVENT_WITH_SPEAKER);

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
                        EventEntry.COLUMN_EVENT_ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_WITH_DATE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.EventEntry.TABLE_NAME,
                        projection,
                        EventEntry.COLUMN_START_TIME + " = '" +
                                EventEntry.getStartTimeFromUri(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_WITH_ROOM:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.EventEntry.TABLE_NAME,
                        projection,
                        EventEntry.COLUMN_ROOM_TITLE + " = '" +
                                EventEntry.getRoomFromUri(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_WITH_TRACK:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.EventEntry.TABLE_NAME,
                        projection,
                        EventEntry.COLUMN_TRACK_ID + " = '" +
                                EventEntry.getTrackIdFromUri(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_WITH_SPEAKER:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ProviderContract.EventEntry.TABLE_NAME,
                        projection,
                        EventEntry.COLUMN_SPEAKER_ID + " = '" +
                                EventEntry.getSpeakerIdFromUri(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
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
                        TrackEntry.COLUMN_TRACK_ID + " = '" + ContentUris.parseId(uri) + "'",
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
                        SpeakerEntry.COLUMN_SPEAKER_ID + " = '" + ContentUris.parseId(uri) + "'",
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
                Log.d(LOG_TAG, "Event");
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_ID:
                Log.d(LOG_TAG, "Event with id");
                return EventEntry.CONTENT_ITEM_TYPE;
            case EVENT_WITH_DATE:
                Log.d(LOG_TAG, "Event with date");
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_ROOM:
                Log.d(LOG_TAG, "Event with room");
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_TRACK:
                Log.d(LOG_TAG, "Event with track");
                return EventEntry.CONTENT_TYPE;
            case EVENT_WITH_SPEAKER:
                Log.d(LOG_TAG, "Event with speaker");
                return EventEntry.CONTENT_TYPE;
            case TRACK:
                Log.d(LOG_TAG, "Track");
                return TrackEntry.CONTENT_TYPE;
            case TRACK_WITH_ID:
                Log.d(LOG_TAG, "Track with id");
                return TrackEntry.CONTENT_ITEM_TYPE;
            case SPEAKER:
                Log.d(LOG_TAG, "Speaker");
                return SpeakerEntry.CONTENT_TYPE;
            case SPEAKER_WITH_ID:
                Log.d(LOG_TAG, "Speaker with id");
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
