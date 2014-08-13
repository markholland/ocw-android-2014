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
}
