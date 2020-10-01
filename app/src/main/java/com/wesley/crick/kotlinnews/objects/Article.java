package com.wesley.crick.kotlinnews.objects;

import org.json.JSONObject;
import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String thumbnail;
    private String html;    //The html used to describe the post ["selftext_html"]
    private String overrideUrl; //Link to external site or image ["url_overridden_by_dest"]
    private ArticleType articleType;
    private String postHint;    // The type of post ["post_hint"]

    private boolean hasThumbnail;

    /**
     * Create the object from the JSON Object
     * @param obj
     */
    public Article(JSONObject obj) {
        this.title = obj.optString("title", "");
        this.thumbnail = obj.optString("thumbnail", "");
        this.html = obj.optString("selftext_html", "");
        this.overrideUrl = obj.optString("url_overridden_by_dest", "");

        this.postHint = obj.optString("post_hint", "");


        validate();
    }

    /**
     * Validate/convert any fields after building the object
     */
    private void validate() {
        // Clear out thumbnail when it is not a link to an image
        if ( !this.thumbnail.isEmpty() ) {
            if (!this.thumbnail.substring(0, 4).equals("http")) {
                this.thumbnail = "";
            }
        }

        hasThumbnail = !this.thumbnail.isEmpty();

        if ( this.html.equals("null") ) this.html = "";

        if (this.postHint.equals("image")) {
            this.articleType = ArticleType.image;
        } else {
            this.articleType = ArticleType.link;
        }
        if ( !this.html.isEmpty() ) {
            this.articleType = ArticleType.text;
        }

        // This url can be a relative path to a subreddit post
        if ( !this.overrideUrl.isEmpty() ) {
            if (this.overrideUrl.substring(0, 3).equals("/r/")) {
                this.overrideUrl = "https://www.reddit.com" + this.overrideUrl;
            }
        }
    }

    /**
     * The title of the article
     */
    public String getTitle() {
        return title;
    }

    /**
     * The image/thumbnail of the article
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * The text of the article in HTML formatting. Can be empty string
     */
    public String getHtml() {
        return html;
    }

    /**
     * Does this article have a thumbnail
     */
    public boolean hasThumbnail() {
        return hasThumbnail;
    }

    /**
     * What type of article is this. @See ArticleType
     */
    public ArticleType getArticleType() {
        return articleType;
    }

    /**
     * The link the article can point too. Can be empty string
     */
    public String getOverrideUrl() {
        return overrideUrl;
    }

    /**
     * The possible types of articles
     */
    public enum ArticleType {
        image, link, text
    }

}
