package com.wesley.crick.kotlinnews.callbacks;

import android.util.Log;

import com.wesley.crick.kotlinnews.objects.ResponseTemplate;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

// Highest Error Code: 1002

public class GenericCallBack implements Callback {

    private SimpleCallback<JSONObject> cb;

    public GenericCallBack(SimpleCallback<JSONObject> cb){
        this.cb = cb;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {

        Log.e("GenericCB", e.getMessage());

        ResponseTemplate<JSONObject> rt = new ResponseTemplate<JSONObject>(1000,
                "Failed to contact server. Please check your internet and try again.");
        cb.call(rt);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        ResponseTemplate<JSONObject> rt = new ResponseTemplate<JSONObject>(-1, "");
        // Validate the response was a success (200)
        if ( response.isSuccessful() ) {
            // Get the json out of the response
            String s = Objects.requireNonNull(response.body()).string();
            Log.e("GenericCB", s);

            JSONObject obj = null;
            // Make sure the response is proper JSON
            try {
                obj = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
                rt = new ResponseTemplate<JSONObject>(1002,
                        "Failed to parse response. Please try again later.");
            }
            // If the JSON object exists then we have the data!
            if ( obj != null ) {
                rt = new ResponseTemplate<JSONObject>(0, "Success");
                rt.obj = obj;
            }
        } else {    // Failed request (404, 500..etc)
            rt = new ResponseTemplate<JSONObject>(1001, "Server Error! Please try again later.");
        }
        cb.call(rt);
    }
}
