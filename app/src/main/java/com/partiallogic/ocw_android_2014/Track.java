package com.partiallogic.ocw_android_2014;

import com.google.gson.annotations.SerializedName;

/**
 * Created by markholland on 13/08/14.
 */
public class Track {

    private String id;
    private String title;
    private String description;
    @SerializedName("color")
    private MyColor color;
    private String excerpt;

    public Track(String id, String title, String description, MyColor color, String excerpt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.excerpt = excerpt;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyColor getColor() {
        return color;
    }

    public void setColor(MyColor color) {
        this.color = color;
    }

    public int getIntColor() {
        return this.color.getColor();
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @Override
    public String toString() {
        String str = "";

        str += this.id + "\n" +
                this.title + "\n" +
                this.description + "\n" +
                getIntColor() + "\n" +
                this.excerpt;

        return str;
    }
}
