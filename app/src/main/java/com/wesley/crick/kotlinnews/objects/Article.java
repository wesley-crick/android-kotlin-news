package com.wesley.crick.kotlinnews.objects;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class Article {
    private String title;
    private String thumbnail;
    private String html;    //The html used to describe the post ["selftext_html"]
    private String overrideUrl; //Link to external site or image ["url_overridden_by_dest"]
    private ArticleType articleType;

    private boolean hasThumbnail;

    public Article(String title, String thumbnail, String html) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.html = html;

        validate();
    }

    public Article(JSONObject obj) {
        this.title = obj.optString("title", "");
        this.thumbnail = obj.optString("thumbnail", "");
        this.html = obj.optString("selftext_html", "");
        this.overrideUrl = obj.optString("url_overridden_by_dest", "");

        String postType = obj.optString("post_hint", "");
        switch(postType) {
            case "image":
                this.articleType = ArticleType.image;
                break;
            default:
                this.articleType = ArticleType.link;
        }
        if ( this.html != "" ) {
            this.articleType = ArticleType.text;
        }

        validate();
    }

    private void validate() {
        // Clear out thumbnail when it is not a link to an image
        if ( !this.thumbnail.substring(0, 4).equals("http")) {
            this.thumbnail = "";
        }

        hasThumbnail = !this.thumbnail.isEmpty();

        // Todo validate some fields
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getHtml() {
        return html;
    }

    public boolean hasThumbnail() {
        return hasThumbnail;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public String getOverrideUrl() {
        return overrideUrl;
    }

    public enum ArticleType {
        image, link, text
    }

}
