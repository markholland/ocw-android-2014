package com.partiallogic.ocw_android_2014;

import com.google.gson.annotations.SerializedName;

/**
 * Created by markholland on 13/08/14.
 */
public class SpeakerData {

    @SerializedName("user")
    private Speaker speaker;

    public SpeakerData(Speaker speaker) {
        this.speaker = speaker;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
