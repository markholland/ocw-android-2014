package com.partiallogic.ocw_android_2014.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by markholland on 12/08/14.
 */
public final class ProviderContract {

    public static final String LOG_TAG = ProviderContract.class.getSimpleName();

    public static final String CONTENT_AUTHORITY = "com.partiallogic.ocw_android_2014.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_EVENT = "event";
    public static final String PATH_TRACK = "track";
    public static final String PATH_SPEAKER = "speaker";
    public static final String PATH_SPEAKS_AT = "speaks_at";

    private ProviderContract() {
        // disallow instantiation
    }

    /* Inner class that defines the table contents of the event table */
    public static final class EventEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;

        public static final String TABLE_NAME = "event";

        public static final String COLUMN_EVENT_ID = "event_id";

        public static final String COLUMN_TITLE = "event_title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_START_TIME = "start_time";

        public static final String COLUMN_END_TIME = "end_time";

        public static final String COLUMN_ROOM_TITLE = "room_title";

        public static final String COLUMN_TRACK_ID = "track_id";

        public static final String COLUMN_SPEAKER_ID = "speaker_ids";

        public static final String ON_DATE = "date";

        public static final String IN_ROOM = "room";

        public static final String OF_TRACK = "track";

        public static final String BY_SPEAKER = "speaker";

        public static final String WITH_SPEAKER_AND_TRACK = "speakertrack";


        public static Uri buildEventUri() {
            return CONTENT_URI;
        }

        public static Uri buildEventByIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildEventByDateUri(String date) {
            return CONTENT_URI.buildUpon().appendPath(ON_DATE).appendPath(date).build();
        }

        public static Uri buildEventByRoomUri(String room) {
            return CONTENT_URI.buildUpon().appendPath(IN_ROOM).appendPath(room).build();
        }

        public static Uri buildEventByTrackUri(String track_id) {
            return CONTENT_URI.buildUpon().appendPath(OF_TRACK).appendPath(track_id).build();
        }

        public static Uri buildEventBySpeakerUri(String speaker_id) {
            return CONTENT_URI.buildUpon().appendPath(BY_SPEAKER).appendPath(speaker_id).build();
        }

        public static Uri buildEventByIdWithSpeakerAndTrackUri(String event_id) {
            return CONTENT_URI.buildUpon().appendPath(WITH_SPEAKER_AND_TRACK)
                    .appendPath(event_id).build();
        }

        public static String getStartTimeFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getRoomFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getTrackIdFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getSpeakerIdFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

    }

    /* Inner class that defines the table contents of the track table */
    public static final class TrackEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRACK).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TRACK;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TRACK;

        public static final String TABLE_NAME = "track";

        public static final String COLUMN_TRACK_ID = "track_id";

        public static final String COLUMN_TITLE = "track_title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_COLOR = "color";

        public static final String COLUMN_EXCERPT = "excerpt";

        public static Uri buildTrackUri() {
            return CONTENT_URI;
        }

        public static Uri buildTrackByIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the table contents of the speaker table */
    public static final class SpeakerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPEAKER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_SPEAKER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_SPEAKER;

        public static final String TABLE_NAME = "speaker";

        public static final String COLUMN_SPEAKER_ID = "speaker_id";

        public static final String COLUMN_FULLNAME = "fullname";

        public static final String COLUMN_AFFILIATION = "affiliation";

        public static final String COLUMN_BIOGRAPHY = "biography";

        public static final String COLUMN_WEBSITE = "website";

        public static final String COLUMN_TWITTER = "twitter";

        public static final String COLUMN_IDENTICA = "identica";

        public static final String COLUMN_BLOG_URL = "blog_url";

        public static Uri buildSpeakerUri() {
            return CONTENT_URI;
        }

        public static Uri buildSpeakerByIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the table contents of the speaks_at table */
    public static final class SpeaksAtEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPEAKS_AT).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_SPEAKS_AT;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_SPEAKS_AT;

        public static final String TABLE_NAME = "speaks_at";

        public static final String COLUMN_EVENT_ID = "event_id";

        public static final String COLUMN_SPEAKER_ID = "speaker_id";

        public static Uri buildSpeaks_atUri() {
            return CONTENT_URI;
        }

        public static Uri buildSpeaks_atByEventIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
