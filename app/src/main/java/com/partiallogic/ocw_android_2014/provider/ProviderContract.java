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

        public static final String COLUMN_EVENT_ID = "id";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_START_TIME = "start_time";

        public static final String COLUMN_END_TIME = "end_time";

        public static final String COLUMN_ROOM_TITLE = "room_title";

        public static final String COLUMN_TRACK_ID = "track_id";

        public static final String COLUMN_SPEAKER_IDS = "speaker_ids";

        public static Uri buildEventUri() {
            return CONTENT_URI;
        }

        public static Uri buildEventByIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildEventByDateUri(String date) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_START_TIME, date).build();
        }

        public static Uri buildEventByRoomUri(String room) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_ROOM_TITLE, room).build();
        }

        public static Uri buildEventByTrackUri(String track_id) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_TRACK_ID, track_id).build();
        }

        public static Uri buildEventBySpeakerUri(String speaker_id) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_SPEAKER_IDS, speaker_id).build();
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

        public static final String COLUMN_TRACK_ID = "id";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_COLOR = "color";

        public static final String COLUMN_EXCERPT = "excerpt";

        public static Uri buildTrackUri() {
            return CONTENT_URI;
        }

        public static Uri BuildTrackByIdUri(long id) {
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

        public static final String COLUMN_SPEAKER_ID = "id";

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

}
