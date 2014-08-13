package com.partiallogic.ocw_android_2014.obj;

/**
 * Created by markholland on 13/08/14.
 */
public class Speaker {

    private String id;
    private String fullname;
    private String affiliation;
    private String biography;
    private String website;
    private String twitter;
    private String identica;
    private String blog_url;

    public Speaker(String id, String fullname, String affiliation, String biography,
                   String website, String twitter, String identica, String blog_url) {

        this.id = id;
        this.fullname = fullname;
        this.affiliation = affiliation;
        this.biography = biography;
        this.website = website;
        this.twitter = twitter;
        this.identica = identica;
        this.blog_url = blog_url;
    }

    @Override
    public String toString() {
        String str = "";

        str += this.id + "\n" +
                this.fullname + "\n" +
                this.affiliation + "\n" +
                this.biography + "\n" +
                this.website + "\n" +
                this.twitter + "\n" +
                this.identica + "\n" +
                this.blog_url;

        return str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getIdentica() {
        return identica;
    }

    public void setIdentica(String identica) {
        this.identica = identica;
    }

    public String getBlog_url() {
        return blog_url;
    }

    public void setBlog_url(String blog_url) {
        this.blog_url = blog_url;
    }
}
