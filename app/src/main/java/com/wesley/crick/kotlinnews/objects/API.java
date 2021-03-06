package com.wesley.crick.kotlinnews.objects;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Make API calls. This is a Singleton Class. Use API.getInstance() to retrieve the object
 */
public class API {
    /// The Singleton
    private static API instance = null;
    /// The main object used to make HTTP requests
    private OkHttpClient client;

    private API() {
        this.client = new OkHttpClient();
    }

    /**
     *
     * @return The singleton instance
     */
    public static API getInstance() {
        if ( instance == null ) {
            instance = new API();
        }
        return instance;
    }

    /**
     * Make an async call to get r/Funny
     *
     * @param cb A Callback to place the result in
     */
    public void getRFunny(Callback cb) {
        Request request = new Request.Builder()
                .url("https://www.reddit.com/r/funny/.json")
                .build();
        client.newCall(request).enqueue(cb);
    }


    /**
     * Make an async call to get r/Kotlin
     *
     * @param cb A Callback to place the result in
     */
    public void getRKotlin(Callback cb) {
        Request request = new Request.Builder()
                .url("https://www.reddit.com/r/kotlin/.json")
                .build();
        client.newCall(request).enqueue(cb);
    }

}
