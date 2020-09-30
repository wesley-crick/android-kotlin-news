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

        if ( this.thumbnail == null ) {
            hasThumbnail = false;
        } else hasThumbnail = !this.thumbnail.isEmpty();
    }

    public Article(JSONObject obj) {
        this.title = obj.optString("title", "");
        this.thumbnail = obj.optString("thumbnail", "");
        this.html = obj.optString("selftext_html", "");
        this.url = obj.optString("url", "");

        //ToDo validate most fields have been filled
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
