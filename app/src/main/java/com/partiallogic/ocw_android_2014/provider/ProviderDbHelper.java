package com.partiallogic.ocw_android_2014.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.partiallogic.ocw_android_2014.provider.ProviderContract.EventEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.SpeakerEntry;
import com.partiallogic.ocw_android_2014.provider.ProviderContract.TrackEntry;

/**
 * Created by markholland on 12/08/14.
 */
public class ProviderDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = ProviderDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ocw.db";

    public ProviderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TrackEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SpeakerEntry.TABLE_NAME + ";");
        createTables(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void createTables(SQLiteDatabase db) {

        String sql;

        // Create Event table
        StringBuilder EventBuilder = new StringBuilder();
        EventBuilder.append("CREATE TABLE " + EventEntry.TABLE_NAME + "(");
        EventBuilder.append(EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        EventBuilder.append(EventEntry.COLUMN_EVENT_ID + " INTEGER NOT NULL, ");
        EventBuilder.append(EventEntry.COLUMN_TITLE + " TEXT NOT NULL, ");
        EventBuilder.append(EventEntry.COLUMN_DESCRIPTION + " TEXT, ");
        EventBuilder.append(EventEntry.COLUMN_START_TIME + " TEXT NOT NULL, ");
        EventBuilder.append(EventEntry.COLUMN_END_TIME + " TEXT NOT NULL, ");
        EventBuilder.append(EventEntry.COLUMN_ROOM_TITLE + " TEXT, ");
        EventBuilder.append(EventEntry.COLUMN_TRACK_ID + " TEXT, ");
        EventBuilder.append(EventEntry.COLUMN_SPEAKER_IDS + " TEXT, ");
        EventBuilder.append(" UNIQUE (" + EventEntry.COLUMN_EVENT_ID +") ON CONFLICT REPLACE");
        EventBuilder.append(" FOREIGN KEY (" + EventEntry.COLUMN_TRACK_ID + ") REFERENCES " +
                TrackEntry.TABLE_NAME + " (" + TrackEntry.COLUMN_TRACK_ID + ") ");
        EventBuilder.append(");");

        sql = EventBuilder.toString();
        Log.i(LOG_TAG, "Creating DB table with string: '" + sql + "'");

        db.execSQL(sql);


        // Create Track table
        StringBuilder TrackBuilder = new StringBuilder();
        TrackBuilder.append("CREATE TABLE " + TrackEntry.TABLE_NAME + "(");
        TrackBuilder.append(TrackEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        TrackBuilder.append(TrackEntry.COLUMN_TRACK_ID + " INTEGER NOT NULL, ");
        TrackBuilder.append(TrackEntry.COLUMN_TITLE + " TEXT NOT NULL, ");
        TrackBuilder.append(TrackEntry.COLUMN_DESCRIPTION + " TEXT, ");
        TrackBuilder.append(TrackEntry.COLUMN_COLOR + " TEXT, ");
        TrackBuilder.append(TrackEntry.COLUMN_EXCERPT + " TEXT, ");
        TrackBuilder.append(" UNIQUE (" + TrackEntry.COLUMN_TRACK_ID +") ON CONFLICT REPLACE");
        TrackBuilder.append(");");

        sql = TrackBuilder.toString();
        Log.i(LOG_TAG, "Creating DB table with string: '" + sql + "'");

        db.execSQL(sql);


        // Create Speaker table
        StringBuilder SpeakerBuilder = new StringBuilder();
        SpeakerBuilder.append("CREATE TABLE " + SpeakerEntry.TABLE_NAME + "(");
        SpeakerBuilder.append(SpeakerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_SPEAKER_ID + " INTEGER NOT NULL, ");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_FULLNAME + " TEXT NOT NULL, ");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_AFFILIATION + " TEXT, ");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_BIOGRAPHY + " TEXT, ");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_WEBSITE + " TEXT, ");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_TWITTER + " TEXT, ");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_IDENTICA + " TEXT, ");
        SpeakerBuilder.append(SpeakerEntry.COLUMN_BLOG_URL + " TEXT, ");
        SpeakerBuilder.append(" UNIQUE (" + SpeakerEntry.COLUMN_SPEAKER_ID +") ON CONFLICT REPLACE");
        SpeakerBuilder.append(");");

        sql = SpeakerBuilder.toString();
        Log.i(LOG_TAG, "Creating DB table with string: '" + sql + "'");

        db.execSQL(sql);
    }


}




























