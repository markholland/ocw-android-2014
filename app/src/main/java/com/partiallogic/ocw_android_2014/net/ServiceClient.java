package com.partiallogic.ocw_android_2014.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.partiallogic.ocw_android_2014.obj.Track;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by markholland on 13/08/14.
 */
public class ServiceClient {
    private static ServiceClient instance;
    public static final String BASE_URL = "http://opensourcebridge.org";

    private RestAdapter mRestAdapter;
    private Map<String, Object> mClients = new HashMap<String, Object>();


    private String mBaseUrl = BASE_URL;

    private ServiceClient() {

    }

    public static ServiceClient getInstance() {
        if (null == instance) {
            instance = new ServiceClient();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getClient(Context context, Class<T> clazz) {
        if (mRestAdapter == null) {
            mRestAdapter = new RestAdapter.Builder().
                    setEndpoint(mBaseUrl).
                    setClient(new OkClient()).
                    build();
        }
        T client = null;
        if ((client = (T) mClients.get(clazz.getCanonicalName())) != null) {
            return client;
        }
        client = mRestAdapter.create(clazz);
        mClients.put(clazz.getCanonicalName(), client);
        return client;
    }

    public void setRestAdapter(RestAdapter restAdapter) {

        mRestAdapter = restAdapter;
    }


    class TrackDeserializer implements JsonDeserializer<Track>
    {
        @Override
        public Track deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
                throws JsonParseException
        {
            // Get the "track" element from the parsed JSON
            JsonElement track = je.getAsJsonObject().get("track");

            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(track, Track.class);

        }
    }
}

