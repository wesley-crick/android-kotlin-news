package com.wesley.crick.kotlinnews.objects;

import org.json.JSONObject;

public class Article {
    private String title;
    private String thumbnail;
    private String html;    //selftext_html
    private String url;

    private boolean hasThumbnail;

    public Article(String title, String thumbnail, String html, String url) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.html = html;
        this.url = url;

        validate();
    }

    public Article(JSONObject obj) {
        this.title = obj.optString("title", "");
        this.thumbnail = obj.optString("thumbnail", "");
        this.html = obj.optString("selftext_html", "");
        this.url = obj.optString("url", "");

        validate();
    }

    private void validate() {
        // Clear out thumbnail when it is not a link to an image
        if ( !this.thumbnail.substring(0, 4).equals("http")) {
            this.thumbnail = "";
        }

        if ( this.thumbnail == null ) {
            hasThumbnail = false;
        } else hasThumbnail = !this.thumbnail.isEmpty();

        // Todo validate some fields
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getHTML() {
        return html;
    }

    public String getUrl() {
        return url;
    }

    public boolean hasThumbnail() {
        return hasThumbnail;
    }
}
