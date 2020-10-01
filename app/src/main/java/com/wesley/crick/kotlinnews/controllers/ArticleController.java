package com.wesley.crick.kotlinnews.controllers;

import com.wesley.crick.kotlinnews.objects.API;
import com.wesley.crick.kotlinnews.callbacks.GenericCallBack;
import com.wesley.crick.kotlinnews.callbacks.SimpleCallback;
import com.wesley.crick.kotlinnews.objects.Article;
import com.wesley.crick.kotlinnews.objects.ResponseTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Highest Error Code: 2005

/**
 * Contains methods to get articles
 */
public class ArticleController {
    /// The Singleton
    private static ArticleController instance = null;

    private ArticleController() { }

    /**
     *
     * @return The singleton instance
     */
    public static ArticleController getInstance() {
        if ( instance == null ) {
            instance = new ArticleController();
        }
        return instance;
    }

    /**
     * Get articles from r/Funny
     * @param cb A callback for the results of the request
     */
    public void getFunnyArticles(final SimpleCallback<Article[]> cb) {
        API.getInstance().getRFunny( new GenericCallBack(
                ( ResponseTemplate<JSONObject> rt) -> this.articlesRetrieved(rt, cb))
        );
    }

    /**
     * Get articles from r/Kotlin
     * @param cb A callback for the results of the request
     */
    public void getKotlinArticles(final SimpleCallback<Article[]> cb) {
        API.getInstance().getRKotlin( new GenericCallBack(
                ( ResponseTemplate<JSONObject> rt) -> this.articlesRetrieved(rt, cb))
        );
    }

    /**
     * Manages the success of failure of getting the articles
     * @param res The response
     * @param cb The callback to call when complete (success or fail)
     */
    private void articlesRetrieved(ResponseTemplate<JSONObject> res, SimpleCallback<Article[]> cb) {

        // Response failed
        if ( res.getCode() != 0 ) {
            cb.call(new ResponseTemplate<>(res.getCode(), res.getMessage()));
            return;
        }
        if ( res.obj == null ) {
            cb.call(new ResponseTemplate<>(2001, "Failed to retrieve response. Please try again later."));
            return;
        }

        // Response was a success!
        JSONArray arr = new JSONArray();
        try {   // Get obj.data{}.children[]
            arr = res.obj.getJSONObject("data").getJSONArray("children");
        } catch (JSONException e) {
            e.printStackTrace();
            cb.call(new ResponseTemplate<>(2003, "Failed to parse response. Please try again later."));
            return;
        }

        if ( arr.length() == 0 ) {
            cb.call(new ResponseTemplate<>(2002, "No data returned."));
            return;
        }

        Article[] articles = new Article[arr.length()];
        for( int i = 0; i < arr.length(); i++) {
            JSONObject obj = null;
            try {   // Get a single object out of the array.        obj.data.children[i].data
                obj = arr.getJSONObject(i).getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
                cb.call(new ResponseTemplate<>(2004, "Failed to parse response. Please try again later."));
                return;
            }
            articles[i] = new Article(obj);
        }

        ResponseTemplate<Article[]> rt = new ResponseTemplate<>(0, "Articles");
        rt.obj = articles;
        cb.call(rt);

    }
}
