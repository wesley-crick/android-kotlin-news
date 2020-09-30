package com.wesley.crick.kotlinnews.controllers;

import com.wesley.crick.kotlinnews.API;
import com.wesley.crick.kotlinnews.callbacks.GenericCallBack;
import com.wesley.crick.kotlinnews.callbacks.SimpleCallback;
import com.wesley.crick.kotlinnews.objects.Article;
import com.wesley.crick.kotlinnews.objects.ResponseTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public void getFunnyArticles(final SimpleCallback<Article[]> cb) {
        API.getInstance().getRFunny( new GenericCallBack( ( ResponseTemplate<JSONObject> rt) -> this.articlesRetrieved(rt, cb)));
    }

    public void getKotlinArticles(final SimpleCallback<Article[]> cb) {
        API.getInstance().getRKotlin( new GenericCallBack( ( ResponseTemplate<JSONObject> rt) -> this.articlesRetrieved(rt, cb)));
    }

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
        try {
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
            try {
                obj = arr.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                cb.call(new ResponseTemplate<>(2004, "Failed to parse response. Please try again later."));
                return;
            }

            if ( obj != null ) {
                try {
                    obj = obj.getJSONObject("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                    cb.call(new ResponseTemplate<>(2005, "Failed to parse response. Please try again later."));
                    return;
                }
                articles[i] = new Article(obj);
            }

        }

        ResponseTemplate<Article[]> rt = new ResponseTemplate<>(0, "Articles");
        rt.obj = articles;
        cb.call(rt);

    }
}
